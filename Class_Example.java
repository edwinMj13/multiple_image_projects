package com.example.myapplication.Ex_Class;

public class Class_Example {

    public String title;
    public  String startValue;
    public String endValue;


    public Class_Example(String title,String startValue,String endValue){
        this.title=title;
        this.startValue=startValue;
        this.endValue=endValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }
}
