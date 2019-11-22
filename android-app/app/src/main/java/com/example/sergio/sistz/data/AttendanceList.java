package com.example.sergio.sistz.data;

/**
 * Created by jlgarcia on 20/03/2018.
 */

public class AttendanceList {

    private String student_code;
    private String full_name;
    private String cb_absence;
    private String sp_reason;

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCb_absence() {
        return cb_absence;
    }

    public void setCb_absence(String cb_absence) {
        this.cb_absence = cb_absence;
    }

    public String getSp_reason() {
        return sp_reason;
    }

    public void setSp_reason(String sp_reason) {
        this.sp_reason = sp_reason;
    }
}
