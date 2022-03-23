package com.example.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextAttributeListener implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        // com.example.listener.ContextParameterListener 里的属性设置也会触发此方法执行
        System.out.printf("attributeAdded name = %s, value = %s%n", name, value);
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        // remove 一个不存在的 attributeName 不会执行本方法
        System.out.printf("attributeRemoved name = %s, value = %s%n", name, value);
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        String name = event.getName();
        Object value = event.getValue();
        // 打印被覆盖的 name 和 value
        // 不打印覆盖后的 name 和 value
        // 覆盖前后的 name 和 value 相同，也会执行本方法
        System.out.printf("attributeReplaced name = %s, value = %s%n", name, value);
    }
}
