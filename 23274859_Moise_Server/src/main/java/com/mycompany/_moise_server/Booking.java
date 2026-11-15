/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

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
    //this method may report InvalidCommandException, run method will catch the exception
    public static String validateDate(String date) throws InvalidCommandException {
        //assign user entered booking date as a String to bookingDate
        String bookingDateIn = date.trim();

        //defines expected pattern of date
        DateTimeFormatter formatter =  new DateTimeFormatterBuilder().parseCaseInsensitive()
                .appendPattern("d MMMM uuuu").toFormatter();

        try {

            //converts bookingDate to LocalDate and check if it matches pattern in formatter
            LocalDate bookingDate = LocalDate.parse(bookingDateIn, formatter);
            
            //return the date as a string
            return bookingDate.format(formatter);

        } catch (DateTimeParseException e) {
            throw new InvalidCommandException("Invalid Date format, use e.g(18 November 2026)");

        }
    }
    //this method may report InvalidCommandException, run method will catch the exception
     public static String validateTime(String time) throws InvalidCommandException{
         //assign user entered booking time as a String to bookingTime
         String bookingTimeIn = time.trim();
         //defines expected pattern of time
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("h a").toFormatter();
        
        try{
            //converts bookingDate to LocalDate and check if it matches pattern in formatter
            LocalTime bookingTime = LocalTime.parse(bookingTimeIn, timeFormatter);
            
            //return the date as a string
            return bookingTime.format(timeFormatter);
        }catch(DateTimeParseException e){
            throw new  InvalidCommandException("Invalid Time format, use e.g(2 am");
        }
     }
     
    public String serverMsg(){
        return workshopName+" on "+date+" at "+time;
    }
    
    public String saveFormat(){
        return memberName+";"+workshopName+";"+date+";"+time;
    }
}


