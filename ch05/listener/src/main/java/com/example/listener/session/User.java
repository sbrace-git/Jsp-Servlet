package com.example.listener.session;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class User implements HttpSessionBindingListener, HttpSessionActivationListener, Serializable {

    private String name;
    private Integer age;

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.printf("sessionWillPassivate %s%n", this);
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.printf("sessionDidActivate %s%n", this);
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        System.out.printf("User # valueBound name = %s, value = %s%n", name, value);
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        // replace : value = null. 先执行 valueBound 再执行 valueUnbound
        // remove : value = removed value
        System.out.printf("User # valueUnbound name = %s, value = %s%n", name, value);
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
