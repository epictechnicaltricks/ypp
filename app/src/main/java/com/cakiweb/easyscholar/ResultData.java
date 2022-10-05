package com.cakiweb.easyscholar;

public class ResultData {
    String resultClass;
    String class_name;


    String grade;
    String exam_term;
    String session_name;
    String teacher_remarks;
    String author;


    public ResultData(String  class_name,
                      String  exam_term,
                      String  grade,
                      String  session_name,
                      String  teacher_remarks,
                      String  author           ) {


        this.class_name       =class_name;
        this.exam_term        =exam_term;
        this.grade            =grade;
        this.session_name     =session_name;
        this.teacher_remarks  =teacher_remarks;
        this.author           =author;

    }






    public String getClass_name() {
        return class_name;
    }

    public String getGrade() {
        return grade;
    }

    public String getExam_term() {
        return exam_term;
    }

    public String getAuthor() {
        return author;
    }

    public String getTeacher_remarks() {
        return teacher_remarks;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setExam_term(String exam_term) {
        this.exam_term = exam_term;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public void setTeacher_remarks(String teacher_remarks) {
        this.teacher_remarks = teacher_remarks;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}



