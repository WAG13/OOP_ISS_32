package com.oop.task4;

import com.oop.task4.CustomClassLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PrintClassInfo {
    String filePath;
    Class loadedClass;

    public PrintClassInfo(String filePath) {
        CustomClassLoader classLoader = new CustomClassLoader();
        this.filePath = filePath;
        this.loadedClass = classLoader.findClass(filePath);
    }

    public void printInfo() {
        System.out.println("FILEPATH:\t" + filePath);
        printMainInfo();
        printInterfaces();
        printClassType();
        printFields();
        printClasses();
        System.out.println();
    }

    public void printMainInfo() {
        System.out.println("PACKAGE:\t" + loadedClass.getPackage().getName());
        System.out.println(Modifier.toString(loadedClass.getModifiers()) + " " + loadedClass.getSimpleName());
        if (loadedClass.getSuperclass() != null) {
            System.out.println("EXTENDS " + loadedClass.getSuperclass());
        }
    }
    public void printInterfaces() {
        Class[] classes = loadedClass.getInterfaces();
        System.out.print("INTERFACES:\t");
        if (classes.length == 0) {
            System.out.println("None");
            return;
        }
        for (Class currClass : classes) {
            System.out.println(Modifier.toString(currClass.getModifiers())
                    + " " + currClass.getName());
        }
    }

    public void printFields() {
        Field[] fields = loadedClass.getDeclaredFields();
        System.out.print("FIELDS:\t");
        if (fields.length == 0) {
            System.out.println("None");
            return;
        }
        for (Field field : fields) {
            System.out.println(Modifier.toString(field.getModifiers())
                    + " " + field.getType().getName()
                    + " " + field.getName());
        }
    }

    public void printClasses() {
        Class[] classes = loadedClass.getDeclaredClasses();
        System.out.print("SUBCLASSES:\t");
        if (classes.length == 0) {
            System.out.println("None");
            return;
        }
        for (Class currClass : classes) {
            System.out.println(Modifier.toString(currClass.getModifiers())
                    + " " + currClass.getName());
        }
    }

    public void printClassType() {
        System.out.println("IS INTERFACE:\t" + loadedClass.isInterface());
        System.out.println("IS ENUM:\t" + loadedClass.isEnum());
    }
}
