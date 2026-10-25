/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author moise
 */
public class BookingMap {
    //linked hash map for sorted collection
    HashMap<Long,Booking> bookings;
    private static long id = 2000;
    
    public BookingMap(){
        bookings = new LinkedHashMap<>();
    }
    //add a new booking
    public void book(Booking b){
        id ++;
        bookings.put(id,b);
        
    }
    
    //remove a booking
    public void cancel(Booking b){
        //iterate through map
        for(Map.Entry<Long,Booking> entry:bookings.entrySet()){
            long id = entry.getKey();
            Booking value = entry.getValue();
            //remove booking that matches the booking passed into the method
            if(value.equals(b)){
                bookings.remove(id);
            }
        }
    }
    
    //list all the person's booking
    public String list(String name){
        //empty string to hold bookings
        String item =  "";
        //iterate through map
        for(Map.Entry<Long,Booking> entry:bookings.entrySet()){
            long id = entry.getKey();
            Booking value = entry.getValue();
            Booking tempVal ;
            //add bookings to
            if(value.getMemberName().equals(name)){
                item+=bookings.get(id)+"-;";
                
            }
        }
        return item;
    }
    
    //count number of bookings
    public int countBookings(String name){
        int count=0;
        
        for(Map.Entry<Long, Booking> entry: bookings.entrySet()){
            Booking value = entry.getValue();
            if(value.getMemberName().equalsIgnoreCase(name)){
                count++;
            }
        }
        return count;
    }
}
