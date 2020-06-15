package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ServiceTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String addQuestion;

    public ServiceTemporal() {
    }

    public ServiceTemporal(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public ServiceTemporal(String name, String email, String addQuestion) {
        this.name = name;
        this.email = email;
        this.addQuestion = addQuestion;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddQuestion() {
        return addQuestion;
    }

    public void setAddQuestion(String addQuestion) {
        this.addQuestion = addQuestion;
    }
}
