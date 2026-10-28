/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author moise
 */
public class BookingMap {

    //linked hash map for sorted collection
    //variable cannot
    private final LinkedHashMap<Long, Booking> bookings;
    private static long id = 2000;

    public BookingMap() {
        bookings = new LinkedHashMap<>();
    }

    //add a new booking
    //synchronize ensure one thread executes it at a time, prevent simultaneous use that results in conflict
    public synchronized String book(Booking b) throws InvalidCommandException {
        if (exists(b)) {
            throw new InvalidCommandException("This booking already exists.");
        }
        id++;
        //add the new booking to the linkedHashMap
        bookings.put(id, b);
        //get number of bookings
        //int numOfBookings = countBookings(b.getMemberName());
        //return b.getMemberName() + "currently has" +numOfBookings+"bookings:" + b.serverMsg() + "\n-;";
        return list(b.getMemberName());

    }

    //remove a booking
    //synchronize ensure one thread executes it at a time, prevent simultaneous use that results in conflict
    public synchronized String cancel(Booking b) throws InvalidCommandException {
        String result = "";
        //variable for key of booking to remove
        Long cancelledBooking = null;
        //iterate through map
        for (Map.Entry<Long, Booking> entry : bookings.entrySet()) {
            long id = entry.getKey();
            Booking value = entry.getValue();
            //remove booking that matches the booking passed into the method
            if (value.getMemberName().equalsIgnoreCase(b.getMemberName())
                    && value.getWorkshopName().equalsIgnoreCase(b.getWorkshopName())
                    && value.getDate().equalsIgnoreCase(b.getDate())
                    && value.getTime().equalsIgnoreCase(b.getTime())) {
                cancelledBooking = entry.getKey();
                break;
            }
        }
        if (cancelledBooking == null) {
            throw new InvalidCommandException("Booking does not exist");
        }
        //remove the booking
        bookings.remove(cancelledBooking);
        //get the current number of bookings
        int numOfBookings = countBookings(b.getMemberName());
        //message to client
        result = "Cancelled. " + b.getMemberName() + "currently has " + numOfBookings + " booking" + b.serverMsg() + "\n-;";
        return result;
    }

    //list all the person's booking
    //synchronize ensure one thread executes it at a time, prevent simultaneous use that results in conflict
    public synchronized String list(String memberName) {
        //get the number of bookings
        int numOfBookings = countBookings(memberName);

        //string buffer to create
        StringBuffer result = new StringBuffer();

        result.append(memberName);
        result.append(" currently has ");
        //if  numOfBookings is 1 singular booking otherwise bookings
        result.append(numOfBookings == 1 ? " booking: " : " bookings: ");

        //empty string to hold bookings
        String item = "";

        //variable to ensure ; isnlt put before first booking
        boolean first = true;

        //iterate through map
        for (Map.Entry<Long, Booking> entry : bookings.entrySet()) {
            //get booking
            Booking value = entry.getValue();
            //add bookings to
            if (value.getMemberName().equalsIgnoreCase(memberName)) {
                //after the first booking
                if (!first) {
                    result.append("-; ");
                }
                result.append(value.serverMsg());
                //set to false so next bookings have semicolon
                first = false;
            }
        }
        return result.toString();
    }

    //count number of bookings
    //synchronize ensure one thread executes it at a time, prevent simultaneous use that results in conflict
    public synchronized int countBookings(String name) {
        int count = 0;

        for (Map.Entry<Long, Booking> entry : bookings.entrySet()) {
            Booking value = entry.getValue();
            if (value.getMemberName().equalsIgnoreCase(name)) {
                count++;
            }
        }
        //return number of bookings
        return count;
    }

    //method for existing booking
    //synchronize ensure one thread executes it at a time, prevent simultaneous use that results in conflict
    public synchronized boolean exists(Booking b) {
        boolean exist = false;
        //iterate through map
        for (Map.Entry<Long, Booking> entry : bookings.entrySet()) {
            //get  the booking values
            Booking value = entry.getValue();
            //check if the booking exists
            if (value.getMemberName().equalsIgnoreCase(b.getMemberName())
                    && value.getWorkshopName().equalsIgnoreCase(b.getWorkshopName())
                    && value.getDate().equalsIgnoreCase(b.getDate())
                    && value.getTime().equalsIgnoreCase(b.getTime())) {
                //return true if a booking already exists
                return true;
            }
        }
        //return if booking doesn't exist
        return false;
    }
}
