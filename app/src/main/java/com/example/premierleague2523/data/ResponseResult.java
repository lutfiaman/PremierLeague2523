package com.example.premierleague2523.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseResult {
    @SerializedName("events")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
