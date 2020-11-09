package com.oop.task4.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {
    @Test
    void createHuman(){
        //Given
        //When
        Human human = new Human("James","Bond");
        //Then
        assertEquals("James",human.getFirstName());
        assertEquals("Bond",human.getLastName());
    }

    @Test
    void setHumanFirstName(){
        //Given
        Human human = new Human("James","Bond");
        String newFirstName = "Bob";
        //When
        human.setFirstName(newFirstName);
        //Then
        assertEquals("Bob",human.getFirstName());
        assertEquals("Bond",human.getLastName());
    }

    @Test
    void setHumanSecondName(){
        //Given
        Human human = new Human("James","Bond");
        String newLastName = "Smith";
        //When
        human.setLastName(newLastName);
        //Then
        assertEquals("James",human.getFirstName());
        assertEquals("Smith",human.getLastName());
    }

}