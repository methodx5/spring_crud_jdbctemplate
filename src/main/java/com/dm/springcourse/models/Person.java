package com.dm.springcourse.models;

import javax.validation.constraints.*;

public class Person {

    private int id;
    @NotEmpty(message = "Not empty")
    @Size(min = 2, max = 10, message = "Min 2 chars max 10 chars")
    private String name;
    @Min(value = 1, message = "not less than 1")
    private int age;
    @Email(message = "valid email")
    @NotEmpty(message = "Not empty")
    private String email;

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
