package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
public class Rewiev {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String recommended;
    @NotNull
    private String whatDoYouLike;

    public Rewiev() {
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

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getWhatDoYouLike() {
        return whatDoYouLike;
    }

    public void setWhatDoYouLike(String whatDoYouLike) {
        this.whatDoYouLike = whatDoYouLike;
    }
}
