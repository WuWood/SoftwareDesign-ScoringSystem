package pojo;

import java.util.Date;

public class Contest {
    private int id;//id
    private String name;//比赛名
    private String description;//
    private Date StartTime;
    private Date EndTime;
    private int MaxMembers;
    private int CurrentMembers;
    private String UserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public int getMaxMembers() {
        return MaxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        MaxMembers = maxMembers;
    }

    public int getCurrentMembers() {
        return CurrentMembers;
    }

    public void setCurrentMembers(int currentMembers) {
        CurrentMembers = currentMembers;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
