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
    //declare ProcessAction to keep it as shared field rather than create a new one every time
    private final ProcessAction processAction;

    //receive client socket and identifier
    public ClientConnectionRun(Socket connection, ProcessAction processAction) {
        this.client_link = connection;
        //store a shared processAction
        this.processAction = processAction;
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
                            out.println("TERMINATE");
                            System.out.println("TERMINATE");
                            break;
                    }
                    try {
                    //trim to remove white space
                    if (clientMessage.trim().isEmpty()) {
                        //if client send an empty booking
                        throw new InvalidCommandException("Cannot send empty booking.");
                    } else {
                        String response = processAction.processAction(clientMessage);
                        //send the response back to the client
                        out.println(response);

                    }

                    }catch(InvalidCommandException e){
                   //print the error messages
                   out.println("InvalidCommandException: "+e.getMessage()); 
                }
            }
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
}
