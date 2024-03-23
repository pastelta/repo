package ru.course.task.two;

import java.lang.reflect.Proxy;

public class Utils {
    public static <T> T cache(T obj) {
        Class cl = obj.getClass();
        return (T) Proxy.newProxyInstance(cl.getClassLoader()
                , new Class[]{Fractionable.class}
                , new FractionableInvocationHandler(obj));
    }
}
