package com.cakiweb.easyscholar;

public class Cca_result_Data {
    String session_name       ;
    String class_name          ;
    String cca_name            ;
    String position_name           ;
    String cca_date               ;
    String viewaction            ;

    public Cca_result_Data(String session_name,String class_name ,String cca_name,String position_name,String cca_date,String viewaction){
        this.session_name=session_name;
        this.class_name=class_name;
        this.cca_name=cca_name;
        this.position_name =position_name ;
        this.cca_date  =cca_date ;
        this.viewaction=viewaction;

    }

    public String getposition_name () {
        return position_name ;
    }
    public String getsession_name() {
        return session_name;
    }

    public String getclass_name() {
        return class_name;
    }

    public String getcca_name() {
        return cca_name;
    }

    public String getCca_date() {
        return cca_date;
    }

    public String getCca_name() {
        return cca_name;
    }

    public void setCca_name(String cca_name) {
        this.cca_name = cca_name;
    }

    public void setCca_date(String cca_date) {
        this.cca_date = cca_date;
    }

    public String getviewaction() {
        return viewaction;
    }

    public void setsession_name(String session_name) {
        this.session_name = session_name;
    }

    public void setclass_name(String class_name) {
        this.class_name = class_name;
    }

    public void setcca_name(String cca_name) {
        this.cca_name = cca_name;
    }
}

