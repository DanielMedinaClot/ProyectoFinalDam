package edu.fje.proyectofinaldam;

public class StatsList {

    private String seasonYear, gamesPlayed, mpg, ppg, rpg, apg, spg, bpg, topg, fgp, tpp, ftp;

    public StatsList(String seasonYear, String gamesPlayed, String mpg, String ppg, String rpg, String apg, String spg, String bpg, String topg, String fgp, String tpp, String ftp) {
        this.seasonYear = seasonYear;
        this.gamesPlayed = gamesPlayed;
        this.mpg = mpg;
        this.ppg = ppg;
        this.rpg = rpg;
        this.apg = apg;
        this.spg = spg;
        this.bpg = bpg;
        this.topg = topg;
        this.fgp = fgp;
        this.tpp = tpp;
        this.ftp = ftp;
    }

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(String gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public String getMpg() {
        return mpg;
    }

    public void setMpg(String mpg) {
        this.mpg = mpg;
    }

    public String getPpg() {
        return ppg;
    }

    public void setPpg(String ppg) {
        this.ppg = ppg;
    }

    public String getRpg() {
        return rpg;
    }

    public void setRpg(String rpg) {
        this.rpg = rpg;
    }

    public String getApg() {
        return apg;
    }

    public void setApg(String apg) {
        this.apg = apg;
    }

    public String getSpg() {
        return spg;
    }

    public void setSpg(String spg) {
        this.spg = spg;
    }

    public String getBpg() {
        return bpg;
    }

    public void setBpg(String bpg) {
        this.bpg = bpg;
    }

    public String getTopg() {
        return topg;
    }

    public void setTopg(String topg) {
        this.topg = topg;
    }

    public String getFgp() {
        return fgp;
    }

    public void setFgp(String fgp) {
        this.fgp = fgp;
    }

    public String getTpp() {
        return tpp;
    }

    public void setTpp(String tpp) {
        this.tpp = tpp;
    }

    public String getFtp() {
        return ftp;
    }

    public void setFtp(String ftp) {
        this.ftp = ftp;
    }
}
