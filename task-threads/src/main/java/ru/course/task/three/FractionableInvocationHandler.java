package ru.course.task.three;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FractionableInvocationHandler<T> implements InvocationHandler {
    private final T obj;
    private final ConcurrentHashMap<Method, Object> resultCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<StateFraction, Object> stateFractionCache = new ConcurrentHashMap<>();

    public FractionableInvocationHandler(T obj) {
        this.obj = obj;
    }

    public static boolean check(StateFraction fr, ConcurrentHashMap<StateFraction, Object> stateFractionCache) {
        for (Map.Entry<StateFraction, Object> entry : stateFractionCache.entrySet()) {
            if (fr.equals(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

    public static double getResult(StateFraction fr, ConcurrentHashMap<StateFraction, Object> stateFractionCache) {
        if (stateFractionCache.isEmpty()) return 0;
        for (Map.Entry<StateFraction, Object> entry : stateFractionCache.entrySet()) {
            if (fr.equals(entry.getKey())) {
                return (double) entry.getValue();
            }
        }
        return 0;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());

        Field numObj = obj.getClass().getDeclaredField("num");
        numObj.setAccessible(true);
        int num = (int) numObj.get(obj);

        Field denumObj = obj.getClass().getDeclaredField("denum");
        denumObj.setAccessible(true);
        int denum = (int) denumObj.get(obj);

        StateFraction stateFraction = new StateFraction(num, denum, System.currentTimeMillis());

        new Task().start();

        if (m.isAnnotationPresent(Mutator.class)) {
            if (m.getName() == "setNum") {
                stateFraction.setNum((int) args[0]);
            }
            if (m.getName() == "setDenum") {
                stateFraction.setDenum((int) args[0]);
            }
        }

        if (m.isAnnotationPresent(Cache.class)) {
            long lifeTime = m.getDeclaredAnnotation(Cache.class).time();

            if (check(stateFraction, stateFractionCache)) {
                result = getResult(stateFraction, stateFractionCache);
                stateFraction.setStartTime(System.currentTimeMillis() + lifeTime);
            } else {
                result = method.invoke(obj, args);
                stateFraction.setStartTime(System.currentTimeMillis() + lifeTime);
                stateFractionCache.put(stateFraction, result);
            }
        } else {
            result = method.invoke(obj, args);
        }

        //System.out.println(stateFraction);
        //System.out.println(stateFractionCache);
        //System.out.println(result);

        return result;
    }

    private class Task extends Thread {
        @Override
        public void run() {
            stateFractionCache.entrySet().removeIf(entry -> entry.getKey().getStartTime() < System.currentTimeMillis());

            //System.out.println("clearing the cache");
        }
    }

}