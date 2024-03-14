package ru.course.task.two;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class FractionableInvocationHandler implements InvocationHandler {
    private final Object obj;
    private final ConcurrentHashMap<Integer, Object> chmCache = new ConcurrentHashMap<>();

    public FractionableInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        Annotation[] cacheAnnotations = m.getAnnotationsByType(Cache.class);
        if (cacheAnnotations.length > 0) {
            if (chmCache.isEmpty()) {
                result = method.invoke(obj, args);
                Integer key = 1;
                chmCache.put(key, result);
            } else {
                result = chmCache.get(1);
            }
        }
        Annotation[] mutatorAnnotations = m.getAnnotationsByType(Mutator.class);
        if (mutatorAnnotations.length > 0) {
            chmCache.clear();
            result = method.invoke(obj, args);
        }
        return result;
    }
}