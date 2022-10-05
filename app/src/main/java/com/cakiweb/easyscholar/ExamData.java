package com.cakiweb.easyscholar;

public class ExamData {
    String date;
    String time;
    String subject;
    String maxMark;
    String past;
    public ExamData(String date,String time,String subject,String maxMark,String past){
        this.date=date;
        this.time=time;
        this.subject=subject;
        this.maxMark=maxMark;
        this.past=past;
    }

    public String getPast() {
        return past;
    }
    public void setPast(String past) {
        this.past = past;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(String maxMark) {
        this.maxMark = maxMark;
    }
}

