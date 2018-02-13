package org.podpage.ijs;

import com.google.gson.Gson;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class JSWrapper {

    private ScriptEngine engine;
    private Gson gson;

    public JSWrapper(String javaScript) {
        this.gson = new Gson();
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            this.engine = factory.getEngineByName("nashorn");

            this.engine.eval(javaScript);
        } catch (ScriptException e) {
            System.out.println(e);
        }
    }

    public void invokeFields(Class clazz, Object obj) {
        for (Field field : obj.getClass().getFields()) {
            field.setAccessible(true);

            try {
                Object fieldObj = field.get(obj);
                this.engine.put(clazz.getSimpleName() + "_" + field.getName(), fieldObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T invokeMethod(Class<T> returnType, Method method, Object[] objects) {
        try {
            ArrayList<String> args = new ArrayList<>();
            for (Object object : objects) {
                args.add(gson.toJson(object));
            }

            String json = engine.eval("JSON.stringify(" + method.getName() + "(" + String.join(",", args) + "))").toString();
            if (json.startsWith("\"") && json.endsWith("\"")) {
                json = json.substring(1, json.length() - 1);
            }
            return SimpleClassParser.parse(returnType, gson, json);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }
}
