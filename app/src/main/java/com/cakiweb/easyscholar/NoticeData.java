package com.cakiweb.easyscholar;

public class NoticeData {
    String noticeTitle;
    String noticeDescription;
    String noticeTime;
    String noticeId;
    String noticeURL;

    public NoticeData(String noticeTitle,String noticeDescription,String noticeTime,String noticeURL,String noticeId){
        this.noticeDescription=noticeDescription;
        this.noticeTime=noticeTime;
        this.noticeTitle=noticeTitle;
        this.noticeId=noticeId;
        this.noticeURL=noticeURL;

    }

    public String getNoticeId() {
        return noticeId;
    }
    public String getNoticeDescription() {
        return noticeDescription;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }



    public String getNoticeURL() {
        return noticeURL;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
}

