package org.podpage.ijs;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

public class IJS {

    private JSWrapper jsWrapper;

    public IJS(File file) {
        jsWrapper = new JSWrapper(ReadUtil.readFromFile(file));
    }

    public IJS(URL url) {
        jsWrapper = new JSWrapper(ReadUtil.readFromUrl(url));
    }

    public IJS(String javaScript) {
        jsWrapper = new JSWrapper(javaScript);
    }

    public <T> T createInterface(Class<T> interfaceClass) {
        T interfaceObject = (T) Proxy.newProxyInstance(IJS.class.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return jsWrapper.invokeMethod(method.getReturnType(), method, objects);
            }
        });
        jsWrapper.invokeFields(interfaceClass, interfaceObject);
        return interfaceObject;
    }
}
