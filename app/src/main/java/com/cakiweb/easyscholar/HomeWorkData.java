package com.cakiweb.easyscholar;

public class HomeWorkData {
    String month;
    String subName;
    String topic;
    String submitBy;
    String chapter;
    String submittedDate;
    String partExam;
    String doc;
    String homeWorkStatus;
    public HomeWorkData( String month,String subName,String topic,String submitBy,String chapter,String submittedDate,String partExam,String doc,String homeWorkStatus){
        this.month=month;
        this.subName=subName;
        this.topic=topic;
        this.submitBy=submitBy;
        this.chapter=chapter;
        this.submittedDate=submittedDate;
        this.partExam=partExam;
        this.doc=doc;
        this.homeWorkStatus=homeWorkStatus;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubmitBy() {
        return submitBy;
    }

    public void setSubmitBy(String submitBy) {
        this.submitBy = submitBy;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getHomeWorkStatus() {
        return homeWorkStatus;
    }

    public void setHomeWorkStatus(String homeWorkStatus) {
        this.homeWorkStatus = homeWorkStatus;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getPartExam() {
        return partExam;
    }
    public void setPartExam(String partExam) {
        this.partExam = partExam;
    }
}
