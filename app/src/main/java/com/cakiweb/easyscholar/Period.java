package com.cakiweb.easyscholar;

public class Period {
    String periodTime;
    String subName;
    String teacher;
    String lecture;
    String topic;

    public Period(String periodTime, String subName, String teacher, String lecture, String topic) {
        this.periodTime = periodTime;
        this.subName = subName;
        this.teacher = teacher;
        this.lecture = lecture;
        this.topic = topic;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
