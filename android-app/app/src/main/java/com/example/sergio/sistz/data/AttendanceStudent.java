package com.example.sergio.sistz.data;

public class AttendanceStudent {
    private String id;
    private String name;
    private int totalTime;
    private int attendedTime;
    private int reason1;
    private int reason2;
    private int reason3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getAttendedTime() { return attendedTime; }

    public int getReason1() { return reason1; }

    public void setReason1(int reason1) { this.reason1 = reason1; }

    public int getReason2() { return reason2; }

    public void setReason2(int reason2) { this.reason2 = reason2; }

    public int getReason3() { return reason3; }

    public void setReason3(int reason3) { this.reason3 = reason3; }

    public void setAttendedTime(int attendedTime) {
        this.attendedTime = attendedTime;
    }
}
