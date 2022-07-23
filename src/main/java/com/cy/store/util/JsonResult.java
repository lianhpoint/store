package com.cy.store.util;

import lombok.Data;

import java.io.Serializable;

//Json格式的数据进行响应

/**
 * 一般情况下，我们在定义实体类时会实现Serializable接口
 * 一个类只有实现了Serializable接口，它的对象才能被序列化
 * 实现了Serializable接口的类可以被ObjectOutputStream转换为字节流，
 * 同时也可以通过ObjectInputStream再将其解析为对象。例如，我们可以将序列化对象写入文件后，
 * 再次从文件中读取它并反序列化成对象，也就是说，可以使用表示对象及其数据的类型信息和字节在内存中重新创建对象。
 *
 * 什么是序列化？
 * 序列化是将对象状态转换为可保持或传输的格式的过程。与序列化相对的是反序列化，
 * 它将流转换为对象。这两个过程结合起来，可以轻松地存储和传输数据
 * @param <E>
 */
@Data
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String message;

    /**
     * 数据(因为返回的数据不知道是什么类型，所以用泛型来替代)
     * 假如是查询数据，返回的就是User对象，假如注册就什么也不返回，所以需要用泛型来替代
     */
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    //声明关于异常的捕获方法
    public JsonResult(Throwable e) {
        this.message =  e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }


}
