package com.example.zakahdeserved.Models;

import java.io.Serializable;

public class HealthStatuses implements Serializable {
    public String cricketerName;
    public String teamName;

    public HealthStatuses() {

    }

    public HealthStatuses(String cricketerName, String teamName) {
        this.cricketerName = cricketerName;
        this.teamName = teamName;
    }

    public String getCricketerName() {
        return cricketerName;
    }

    public void setCricketerName(String cricketerName) {
        this.cricketerName = cricketerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
