package com.cakiweb.easyscholar;

public class StudyMaterialData {
    String subName;
    String chapterName;
    String topic;
    String month;
    public StudyMaterialData(String subName,String chapterName,String topic,String month){
        this.subName=subName;
        this.chapterName=chapterName;
        this.topic=topic;
        this.month=month;

    }

    public String getTopic() {
        return topic;
    }

    public String getSubName() {
        return subName;
    }

    public String getMonth() {
        return month;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
