package com.example.premierleague2523.data;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("idEvent")
    private int id;

    @SerializedName("strHomeTeam")
    private String teamHome;

    @SerializedName("strAwayTeam")
    private String teamAway;

    @SerializedName("strTimestamp")
    private String resultDate;

    @SerializedName("strVenue")
    private String strVenue;

    @SerializedName("strPostponed")
    private String isPostponed;

    @SerializedName("strStatus")
    private String status;

    @SerializedName("strLeague")
    private String strLeague;

    @SerializedName("intHomeScore")
    private int homeScore;

    @SerializedName("intAwayScore")
    private int awayScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String scheduleDate) {
        this.resultDate = scheduleDate;
    }

    public String getStrVenue() {
        return strVenue;
    }

    public void setStrVenue(String strVenue) {
        this.strVenue = strVenue;
    }

    public String getIsPostponed() {
        return isPostponed;
    }

    public void setIsPostponed(String isPostponed) {
        this.isPostponed = isPostponed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
