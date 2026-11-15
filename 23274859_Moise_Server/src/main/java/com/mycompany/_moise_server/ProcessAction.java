/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

/**
 *
 * @author moise
 */
//class to process client request and validate it
public class ProcessAction {
    //declare BookingMap to keep it as shared field rather than create a new one every time
    private final BookingMap bookingMap;
    public ProcessAction(BookingMap bookingMap) {
        this.bookingMap = bookingMap;
    }
    
    //this method may report InvalidCommandException, run method will catch the exception
    //method handles validation and the action
    public String processAction(String clientMessage)throws InvalidCommandException{
            //-1 ensures trailing empty string is preserved
            String[] parts = clientMessage.trim().split(";", -1);

            //invalid number of fields
            if (parts.length != 5) {
                throw new InvalidCommandException(
                        "Must provide valid details for all fields (action; name; workshop name; date, time am/pm)"
                );
            }
            //invalid action
            String action = parts[0].trim().toLowerCase();
            String name = parts[1].trim();
            String workshopName = parts[2].trim();
            String date = parts[3].trim();
            String time = parts[4].trim();

            //empty values check
            if (action.isEmpty()) {
                throw new InvalidCommandException("Action must be provided");
            }

            if (name.isEmpty()) {
                throw new InvalidCommandException("Name must be provided");
            }
            
            //handle list action, handle earlier since it only needs name
            if(action.equals("list")){
                if(!workshopName.equals("-") || !date.equals("-") || !time.equals("-")){
                    throw new InvalidCommandException("List action format is e.g(list;name; -;-;-");
                }
                //use list method to get all client bookings if it exists
                return bookingMap.list(name);
            }

            if (workshopName.isEmpty()) {
                throw new InvalidCommandException("Work shop name as to be provided");
            }

            if (date.isEmpty()) {
                throw new InvalidCommandException("Date as to be provided");
            }

            if (time.isEmpty()) {
                throw new InvalidCommandException("Time as to be provided");
            }

            //invalid action
            if (!action.equalsIgnoreCase("book") && !action.equalsIgnoreCase("cancel") && !action.equalsIgnoreCase("list")) {
                throw new InvalidCommandException("Not a valid action e.g(book, cancel, list)");
            }

            //validate date is valid
            String validDate = Booking.validateDate(date);
            //validate time 
            String validTime = Booking.validateTime(time);

            //initialize a new Booking object with the variables after validation
            Booking booking = new Booking(name, workshopName, validDate, validTime);
            switch(action){
                case "book":
                    return bookingMap.book(booking);
                case "cancel" :
                    return bookingMap.cancel(booking);
                default:
                    //satisfies compiler
                    throw new InvalidCommandException("Invalid action");
            }
    }

    
}
