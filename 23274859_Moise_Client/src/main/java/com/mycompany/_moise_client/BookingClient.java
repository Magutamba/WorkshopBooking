/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author moise
 */
public class BookingClient {
    public static void main(String[] args){
        // Stores the server IP address that the client will try to contact; replace it if the server uses a different address.
        String host = "localhost";
        // Stores the server TCP port; it must match the port used by EchoServer.
        int port = 1234;
        run();
    }
        //connect to server and start a thread
        private static run(){
            // Starts a try block so networking and input/output errors can be handled safely.
            try{
                // Creates a TCP socket and immediately attempts to connect to the server identified by host and port.
                Socket socket = new Socket(host, port);

                // Prints confirmation after the TCP connection is successfully established.
                System.out.println("Connected to server.");

                //BufferedReader for efficiently reading text sent by the server.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //PrintWriter for sending text to the server; true enables automatic flushing after println().
                PrintWriter out = new PrintWriter(new socket.getOutputStream(),true);

                //dialog box to store user booking
                String userBooking = JOptionPane.showInputDialog("Enter your  booking in e.g(action; name; workshop name; date, time am/pm)");

                // Sends the user's booking to the server as one text line.
                out.println(userBooking);

                // Waits for and reads one complete response line from the server.
                String serverReply = in.readLine();

                // Displays the response received from the server.
                System.out.println("Server Reply: "+serverReply);

               // Closes the client socket and releases the network connection and related resources.
               socket.close();

            }catch(IOException e){
                // Prints a readable description of the error.
                System.out.println("Client error: "+e.getMessage());
            }finally{
                
                try{
                    System.out.println("Closing Connection: ");
                    socket.close();
                }catch(IOException){
                    System.out.println("Unable to disconnect!");
                    System.exit(1);
                }
            }
        }
}
