package com.oop.task4.model;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Student extends Human{

    private String university;
    private int course;

    public Student(String firstName, String lastName, String university, int course) {
        super(firstName, lastName);
        this.university = university;
        this.course = course;
    }

    public void study() {
        System.out.print("I'm studying\n");
    }
}
