package io.github.ljun51.basic;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib case
 *
 * @author lee
 */
public class CglibCase1 {

    static class Sister {
        public void sing() {
            System.out.println("I am Jinsha, a little sister.");
        }
    }

    static class CGLibProxy implements MethodInterceptor {

        private Object target;

        public Object getInstance(Object target) {
            this.target = target;
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(this.target.getClass());
            enhancer.setCallback(this);
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("introduce yourself...");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("score...");
            return result;
        }
    }

    public static void main(String[] args) {
        CGLibProxy proxy = new CGLibProxy();
        Sister proxySister = (Sister) proxy.getInstance(new Sister());
        System.out.println("CGLib Dynamic object name: " + proxySister.getClass().getName());
        proxySister.sing();
    }
}
