package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDupcalitedException;
import com.cy.store.util.JsonResult;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController//等于@Controller+@ResponseBody
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    /**
     控制层如何接收前端的数据?
     1.接收数据方式一：请求处理方法的参数列表设置为pojo类型来接收前端的数据，
     就是后端采用对象来接收数据
     springboot会将前端的URL地址中的参数名和pojo类的属性名进行比较，如果这两个
     名称相同，则将值注入到pojo类中对应的属性上
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
       userService.reg(user);
       return new JsonResult<>(ok);
    }

    /**
     控制层如何接收前端的数据?
     2.接收数据方式一：请求处理方法的参数列表设置为非pojo类型
     就是后端采用对象来接收数据
     springboot会将请求的参数名和方法的参数名直接进行比较，如果名称相同
     则自动完成值的依赖注入

     为什么springboot不需要任何的注解就可以完成注入呢？
     因为springboot约定大于配置的理念，省略了大量的配置甚至注解的编写
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data=userService.login(username,password);
        //向session对象中完成对数据的绑定（session全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //获取session中绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(ok,data);//<>里面的值可以省略不写
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,
                    oldPassword,newPassword);
        return new JsonResult<>(ok);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getuidFromSession(session));
        return new JsonResult<>(ok,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象有四部分数据：username，phone，email，gender
        //uid数据需要再次封装到user对象中
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(ok);
    }

    //设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE=10*1024*1024;

    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE=new ArrayList<>();

    //静态块可以给集合做初始化
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    /**
     * MultipartFile接口：是springmvc提供的一个接口，这个接口为我们包装了获取文件类型的数据
     * （任何类型的file都可以接收），springboot又整合了springmvc，只需在处理请求的方法参数
     * 列表上声明一个参数类型为MultipartFile的参数，然后springboot自动将传递给服务的文件数据
     * 赋值给这个参数
     *
     *
     * MultipartFile接口方法详解：
     * 1.getOriginalFileName方法获取的是文件的完整名称，包括文件名称+文件拓展名
     * 2.getContentType方法获取的是文件的类型，注意是文件的类型，不是文件的拓展名。
     * 3.getInputStream方法用来将文件转换成输入流的形式来传输文件，会抛出IOException异常。
     * 4.transferTo方法用来将接收文件传输到给定目标路径，会抛出IOException、IllegalStateException异常。
     *
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(
            HttpSession session,
            @RequestParam("file") MultipartFile file){
        //判断文件是否为null
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if(file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件大小超出限制");
        }
        //判断文件的类型是否是我们规定的和后缀类型
        String contentType=file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        /**
         * 上传的文件.../upload/文件.png
         *
         * request.getSession().getServletContext() 获取的是Servlet容器对象，
         * 相当于tomcat容器了。getRealPath("/") 获取实际路径，“/”指代项目根目录，
         * 所以代码返回的是项目在容器中的实际发布运行的根路径
         */
        String parent = session.getServletContext().getRealPath("upload");
        //file对象指向这个路径，在判断file是否存在
        File dir = new File(parent);
        if(!dir.exists()){//检测目录是否存在
            dir.mkdirs();//创建当前目录
        }
        //因为如果上传的文件名字相同的话，会直接覆盖掉，所以我们需要自己给文件重新取一个名字
        //获取到这个文件名称，uuid工具将生成一个新的字符串作为文件名
        //此处生成的是例如：test.png的文件格式，不包含目录结构
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename="+originalFilename);
        //获取文件后缀名
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //在dir目录下生成一个名字相同，后缀相同的空文件，然后再把上传的文件内容写入空文件中，达到上传的目的
        File dest = new File(dir, filename);//是一个空文件
        try {
            //将file文件中的数据写入到dest文件中，但是需要保持后缀名一致，不然就会失败
            //transferTo:用来把 MultipartFile 转换换成 File
            file.transferTo(dest);
        }
        catch (FileStateException e){
            throw new FileStateException("文件状态异常");
        }
        catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径(/upload/test.png,记得写/)，相对路径就行，因为是在同一个项目下
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid,avatar,username);//此方法需要三个参数，所以需要先创建处理
        //返回用户头像的路径给前端页面，将来用于头像的展示使用

        return new JsonResult<>(ok,avatar);
    }



    /*
    此类的简略方法在上面，把重复的catch的异常抽取出来变成一个类BaseController
     @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDupcalitedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }
        catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }
     */
}
