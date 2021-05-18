package edu.fje.proyectofinaldam;

public class JugadoresList {

    private String temporaryDisplayName, jersey , pos, nbaDebutYear, country;

    public JugadoresList(String temporaryDisplayName, String jersey, String pos, String nbaDebutYear, String country) {
        this.temporaryDisplayName = temporaryDisplayName;
        this.jersey = jersey;
        this.pos = pos;
        this.nbaDebutYear = nbaDebutYear;
        this.country = country;
    }

    public String getTemporaryDisplayName() {
        return temporaryDisplayName;
    }

    public void setTemporaryDisplayName(String temporaryDisplayName) {
        this.temporaryDisplayName = temporaryDisplayName;
    }

    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNbaDebutYear() {
        return nbaDebutYear;
    }

    public void setNbaDebutYear(String nbaDebutYear) {
        this.nbaDebutYear = nbaDebutYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
