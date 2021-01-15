package com.example.premierleague2523.data;

import com.google.gson.annotations.SerializedName;

public class Schedule {
    private int idDb;

    public int getIdDb() {
        return idDb;
    }

    public void setIdDb(int idDb) {
        this.idDb = idDb;
    }

    @SerializedName("idEvent")
    private int id;

    @SerializedName("strHomeTeam")
    private String teamHome;

    @SerializedName("strAwayTeam")
    private String teamAway;

    @SerializedName("strTimestamp")
    private String scheduleDate;

    @SerializedName("strVenue")
    private String strVenue;

    @SerializedName("strPostponed")
    private String isPostponed;

    @SerializedName("strStatus")
    private String status;

    @SerializedName("strLeague")
    private String strLeague;

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

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
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
}
