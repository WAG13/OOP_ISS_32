package com.oop.task4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomClassLoaderTest {

    @Test
    public void checkLoadingClass(){
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class foundClass = customClassLoader.findClass("com.oop.task4.model.Human");
        assertEquals(foundClass.getName(), "com.oop.task4.model.Human");
    }

    @Test
    public void checkSelfClass(){
        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class foundClass = customClassLoader.findClass("com.oop.task4.CustomClassLoader");
        assertEquals(foundClass.getName(), "com.oop.task4.CustomClassLoader");
    }

}