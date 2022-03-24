package com.example.listener.session;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener {

    private String name;
    private Integer age;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        System.out.printf("MySessionBindingListener # valueBound name = %s, value = %s%n", name, value);
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        // replace : value = null. 先执行 valueBound 再执行 valueUnbound
        // remove : value = removed value
        System.out.printf("MySessionBindingListener # valueUnbound name = %s, value = %s%n", name, value);
    }

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
