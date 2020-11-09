package com.oop.task4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintClassInfoTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void printInfoHuman(){
        PrintClassInfo classInfo = new PrintClassInfo("com.oop.task4.model.Human");
        classInfo.printInfo();
        String expected = ("FILEPATH:\tcom.oop.task4.model.Human\n" +
                "PACKAGE:\tcom.oop.task4.model\npublic Human\n" +
                "EXTENDS class java.lang.Object\n" +
                "INTERFACES:\tNone\n" +
                "IS INTERFACE:\tfalse\n" +
                "IS ENUM:\tfalse\n" +
                "FIELDS:\tprivate java.lang.String firstName\n" +
                "private java.lang.String lastName\n" +
                "SUBCLASSES:\tNone\n\n").replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
        assertEquals( expected, outContent.toString());
    }

    @Test
    public void printInfoStudent(){
        PrintClassInfo classInfo = new PrintClassInfo("com.oop.task4.model.Student");
        classInfo.printInfo();
        String expected = ("FILEPATH:\tcom.oop.task4.model.Student\n" +
                "PACKAGE:\tcom.oop.task4.model\npublic Student\n" +
                "EXTENDS class com.oop.task4.model.Human\n" +
                "INTERFACES:\tNone\n" +
                "IS INTERFACE:\tfalse\n" +
                "IS ENUM:\tfalse\n" +
                "FIELDS:\tprivate java.lang.String university\n" +
                "private int course\n" +
                "SUBCLASSES:\tNone\n\n").replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
        assertEquals( expected, outContent.toString());
    }

}