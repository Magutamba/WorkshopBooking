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
    Socket client_link=null;
    
    //receive client socket and identifier
    public ClientConnectionRun(Socket connection ){
        this.client_link = connection;
    }
    
    //
    public BookingMap(BookingMap bookings){
        this.bookings = bookings;
    }
    //run method as code that the threads run
    public void run(){
        try{
            //BufferedReader receives texts from client,InputStreamReader turns bytes into characters
            //getInputStream get data from client socket
            BufferedReader in = new BufferedReader(new InputStreamReader(client_link.getInputStream()));
            
            //printwriter sends message back to client,getOutputStream() sends data through the socket
            PrintWriter out = new PrintWriter(client_link.getOutputStream(), true);
            
            //read one line of text sent by client
            String message = in.readLine();
            if(message.charAt)
            //display the receive client message in server console
            System.out.println();
            
            //send the message back to the client
            out.println();   
        }catch(IOException e){
            //handle socket errors, input and output 
            e.printStackTrace();
        }
        finally{
            //executes whether success or failure of preceding code
            try{
                //close the socket to releae the connection resources
                client_link.close();
            }catch(IOException e){
                //catch if connection cannot be closed
                System.out.println("Unable to disconnect");
            }
        }
    }
    
}
