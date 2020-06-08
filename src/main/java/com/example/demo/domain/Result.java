package com.example.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @Email
    private String email;
    @Transient
    private String outs;
    @NotNull
    private int age;
    @NotNull
    LocalDateTime ldt;


    public Result() {
    }

    public Result(@NotNull String name, @Email @NotNull String email, String outs, @NotNull int age, @NotNull LocalDateTime ldt) {
        this.name = name;
        this.email = email;
        this.outs = outs;
        this.age = age;
        this.ldt = ldt;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public long getId() {
        return id;
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

    public String getOuts() {
        return outs;
    }

    public void setOuts(String outs) {
        this.outs = outs;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }


}
