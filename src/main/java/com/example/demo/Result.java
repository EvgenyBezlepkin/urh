package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @Transient
    private String outs;
    @NotNull
    private int age;
    @NotNull
    LocalDateTime ldt;

    @OneToOne
    private IntermediateResult intermediateResult;

    public Result() {
    }

    public Result(@NotNull int age, @NotNull String name, @NotNull String outs, IntermediateResult intermediateResult, LocalDateTime ldt) {
        this.name = name;
        this.outs = outs;
        this.age = age;
        this.intermediateResult = intermediateResult;
        this.ldt = ldt;
    }

    public Result(@NotNull int age, @NotNull String name, @NotNull String outs, IntermediateResult intermediateResult) {
        this.name = name;
        this.outs = outs;
        this.age = age;
        this.intermediateResult = intermediateResult;
    }


    public IntermediateResult getIntermediateResult() {
        return intermediateResult;
    }

    public void setIntermediateResult(IntermediateResult intermediateResult) {
        this.intermediateResult = intermediateResult;
    }

    public int getId() {
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

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }
}
