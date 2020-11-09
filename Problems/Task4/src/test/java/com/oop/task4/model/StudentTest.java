package com.oop.task4.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void createStudent(){
        //Given
        //When
        Student student = new Student("James","Bond","KNU",3);
        //Then
        assertEquals("James", student.getFirstName());
        assertEquals("Bond", student.getLastName());
        assertEquals("KNU", student.getUniversity());
        assertEquals(3, student.getCourse());
    }

    @Test
    void setStudentUniversity(){
        //Given
        Student student = new Student("James","Bond","KNU",3);
        String newUniversity = "KPI";
        //When
        student.setUniversity(newUniversity);
        //Then
        assertEquals("KPI", student.getUniversity());
    }

    @Test
    void setStudentCourse(){
        //Given
        Student student = new Student("James","Bond","KNU",3);
        //When
        student.setCourse(4);
        //Then
        assertEquals(4, student.getCourse());
    }

    @Test
    void studyStudentCourse(){
        //Given
        Student student = new Student("James","Bond","KNU",3);
        //When
        student.study();
        //Then
        assertEquals("I'm studying\n", outContent.toString());
    }

}