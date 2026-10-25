/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

/**
 *
 * @author moise
 */
public class Booking {
    private String memberName, workshopName,date,time;

    public Booking(String memberName, String workshopName, String date, String time) {
        this.memberName = memberName;
        this.workshopName = workshopName;
        this.date = date;
        this.time = time;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
//    public String toString(){
//        return "book;"+memberName+";"+workshopName+";"+date+";"+time;
//    }
    public String serverMsg(){
        return workshopName+" on"+date+" at "+time;
    }
    
    public String saveFormat(){
        return memberName+";"+workshopName+";"+date+";"+time;
    }
}


