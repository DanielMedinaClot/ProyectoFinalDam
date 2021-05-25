package edu.fje.proyectofinaldam;

import java.io.Serializable;

//Clase para generar objectos de Draft para el Recycler
public class DraftList implements Serializable {

    public String prospect;
    public int number;
    public String team;
    public String position;
    public String college;
    public String country;
    public String height;
    public String weight;
    public String headshot;


    public DraftList(String prospect, int number, String team, String position, String college, String country, String height, String weight, String headshot) {
        this.prospect = prospect;
        this.number = number;
        this.team = team;
        this.position = position;
        this.college = college;
        this.country = country;
        this.height = height;
        this.weight = weight;
        this.headshot = headshot;
    }


    public String getProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }
}
