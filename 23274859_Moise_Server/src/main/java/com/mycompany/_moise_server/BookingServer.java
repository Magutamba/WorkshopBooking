/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

import java.net.ServerSocket;

/**
 *
 * @author moise
 */
//main Booking server class, listens to incoming clientconnections and asssigns each to a seperate ClientConnectionRun thread
public class BookingServer {
    //Serverscoket listens for client requesting a connection
    private static ServerSocket servSock;
    
    //port number on which the server listens
    private static int PORT = 1234;
    
    //counts the number of clients accepted by the server
    private static int clientConnections = 0;
    
    public static void main(String[] args){
        
        System.out.println("Opening port...\n");
        
        try{
            //create server socket on port 1234
            //the server beegins listening for client connections
            servSock = new ServerSocket(PORT);
 
        }catch(IOException e){
            //if port cannot be opened
            System.out.println("Unable to open port!");
            
             //terminate programm as port cannot be opened
             System.exit(1);
            
        }
        
        //keep server running to accpet new clients
        do{
            run();
        }while(true);
        
        //accept a client connection and start a thread
        private static void run(){
            //declare a socket for communication with a client
            Socket link;
            
            try{
                //wait for a client to connect
                // this method blocks until an incoming connection is received
                //one connected, accept() returns a socket
                // Once connected, accept() returns a Socket for communicating with that client.
                link =servSock.accept();
                
                //handler to communicate with the client
                
                Runnable resource = new ClientConnectionRun(link);
                
                //seperate thread for the client handler
                Thread t = new Thread(resource);
                
                //start the thread to handle client, independent of future connection requests
                t.start();
            }catch(IOException e1){
                //handle error while accepting connection
                
            }
        }
    }
    
    
}
