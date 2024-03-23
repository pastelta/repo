package ru.course.task.two;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class FractionableInvocationHandler<T> implements InvocationHandler {
    private final T obj;
    private final ConcurrentHashMap<Method, Object> resultCache = new ConcurrentHashMap<>();

    public FractionableInvocationHandler(T obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (m.isAnnotationPresent(Cache.class)) {
            if (resultCache.isEmpty()) {
                result = method.invoke(obj, args);
                resultCache.put(method, result);
            } else {
                result = resultCache.get(method);
            }
        } else if (m.isAnnotationPresent(Mutator.class)) {
            resultCache.clear();
            result = method.invoke(obj, args);
        } else {
            result = method.invoke(obj, args);
        }
        return result;
    }
}