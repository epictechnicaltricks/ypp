package com.cakiweb.easyscholar;

public class BookData {
    String bookName;
    String bookNo;
    String bookIssue;
    String bookReturn;
    String bookDue;
    String bookFine;
    public BookData(String bookName,String bookNo,String bookIssue,String bookReturn,String bookDue,String bookFine){
        this.bookName=bookName;
        this.bookNo=bookNo;
        this.bookIssue=bookIssue;
        this.bookReturn=bookReturn;
        this.bookDue=bookDue;
        this.bookFine=bookFine;
    }

    public String getBookDue() {
        return bookDue;
    }

    public String getBookFine() {
        return bookFine;
    }

    public String getBookIssue() {
        return bookIssue;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookNo() {
        return bookNo;
    }

    public String getBookReturn() {
        return bookReturn;
    }

    public void setBookDue(String bookDue) {
        this.bookDue = bookDue;
    }

    public void setBookFine(String bookFine) {
        this.bookFine = bookFine;
    }

    public void setBookIssue(String bookIssue) {
        this.bookIssue = bookIssue;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }

    public void setBookReturn(String bookReturn) {
        this.bookReturn = bookReturn;
    }
}
