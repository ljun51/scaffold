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
public class CglibCase2 {

    static class CglibDynamicProxy implements MethodInterceptor {

        private Object target;

        public CglibDynamicProxy(Object target) {
            this.target = target;
        }

        public <T> T getProxy() {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(target.getClass());
            enhancer.setCallback(this);
            return (T) enhancer.create();
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("CglibDynamicProxy intercept 方法执行前-------------------------------");

            System.out.println("obj = " + obj.getClass());
            System.out.println("method = " + method);
            System.out.println("proxy = " + proxy);

            Object object = proxy.invoke(target, args);
            System.out.println("CglibDynamicProxy intercept 方法执行后-------------------------------");
            return object;
        }
    }

    public static void main(String[] args) {
        Service target = new Service();
        CglibDynamicProxy proxy = new CglibDynamicProxy(target);
        Service proxyObject = proxy.getProxy();
        proxyObject.finalMethod();
        proxyObject.publicMethod();
    }
}
