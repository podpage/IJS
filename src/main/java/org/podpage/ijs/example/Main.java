package org.podpage.ijs.example;

import org.podpage.ijs.IJS;

import java.net.URL;

public class Main {

    /**
    Code on https://podpage.org/js/java.js

    function test(test) {
        return test
    }
    **/

    public static void main(String... args) throws Exception {
        IJS ijs = new IJS(new URL("https://podpage.org/js/java.js"));
        ITest iTest = ijs.createInterface(ITest.class);
        System.out.println(iTest.test("test"));
    }
}