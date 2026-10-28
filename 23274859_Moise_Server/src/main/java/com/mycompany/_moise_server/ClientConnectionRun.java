/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author moise
 */
//implement runnable so class can execute in seperate thread, each instance handles communication with one connected client
public class ClientConnectionRun implements Runnable {

    //Socket used to connect with client
    Socket client_link = null;
    //declare BookingMap to keep it as shared field rather than create a new one every time
    private final BookingMap bookingMap;

    //receive client socket and identifier
    public ClientConnectionRun(Socket connection,BookingMap bookingMap) {
        this.client_link = connection;
        this.bookingMap = bookingMap;
    }

    //run method as code that the threads run
    public void run() {
        try {
            //BufferedReader receives texts from client,InputStreamReader turns bytes into characters
            //getInputStream get data from client socket
            BufferedReader in = new BufferedReader(new InputStreamReader(client_link.getInputStream()));

            //printwriter sends message back to client,getOutputStream() sends data through the socket
            PrintWriter out = new PrintWriter(client_link.getOutputStream(), true);

            //client message
            String clientMessage;
            
                //continue receiving commands from client, when terminated in return null
                while ((clientMessage = in.readLine()) != null) {
                    //terminate if client sends stop
                    if (clientMessage.equalsIgnoreCase("Stop")) {
                            System.out.println("TERMINATE");
                            break;
                    }
                    try {
                    //trim to remove white space
                    if (clientMessage.trim().isEmpty()) {
                        //if client send an empty booking
                        throw new InvalidCommandException("Cannot send empty booking.");
                    } else {
                        String response = processAction(clientMessage);
                        out.println(response);

                    }

                    }catch(InvalidCommandException e){
                   //print the error messages
                   out.println("InvalidCommandException: "+e.getMessage()); 
                }
            }
            //display the receive client message in server console
            System.out.println();

            //send the message back to the client
            out.println();
        } catch (IOException e) {
            //handle socket errors, input and output 
            e.printStackTrace();
        } finally {
            //executes whether success or failure of preceding code
            try {
                //close the socket to releae the connection resources
                client_link.close();
            } catch (IOException e) {
                //catch if connection cannot be closed
                System.out.println("Unable to disconnect");
            }
        }
    }
    
    //this method may report InvalidCommandException, run method will catch the exception
    //method handles validation and the action
    private String processAction(String clientMessage)throws InvalidCommandException{
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
                //if client as bookings
                if(bookingMap.countBookings(name)!=0){
                    return bookingMap.list(name);
                }
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
                    //use exists method to check for already existing bookings
                    /*if (bookingMap.exists(booking)) {
                        throw new InvalidCommandException("This booking already exists.");
                    } else {
                        return bookingMap.book(booking);
                    }*/
                    return bookingMap.book(booking);
                case "cancel" :
                    //cancel booking if it exists
                    /*if (!bookingMap.exists(booking)) {
                        throw new InvalidCommandException("This booking doesn't exists.");
                    } else {
                       return bookingMap.cancel(booking);
                    }*/
                    return bookingMap.cancel(booking);
                default:
                    //satisfies compiler
                    throw new InvalidCommandException("Invalid action");
            }
    }

}
