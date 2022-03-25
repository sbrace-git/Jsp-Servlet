package com.example.sessionactivation;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class Person implements HttpSessionActivationListener, Serializable {

    private String name;

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.printf("sessionWillPassivate %s%n", this);
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.printf("sessionDidActivate %s%n", this);
    }

    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
