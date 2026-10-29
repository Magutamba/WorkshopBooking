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
    // Stores the server IP address that the client will try to contact; replace it if the server uses a different address.
    private static String host = "localhost";
    // Stores the server TCP port; it must match the port used by EchoServer.
    private static int port = 1234;

    public static void main(String[] args) {
        run();
    }
    //connect to server and start a thread

    private static void run() {
        // Starts a try block so networking and input/output errors can be handled safely.
        try {
            // Creates a TCP socket and immediately attempts to connect to the server identified by host and port.
            Socket socket = new Socket(host, port);

            // Prints confirmation after the TCP connection is successfully established.
            System.out.println("Connected to server.");

            //BufferedReader for efficiently reading text sent by the server.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //PrintWriter for sending text to the server; true enables automatic flushing after println().
            PrintWriter out = new PrintWriter(new socket.getOutputStream(), true);

            //dialog box to store user message
            String userMessage = JOptionPane.showInputDialog(
                    "Enter your  booking in e.g(action; name; workshop name; date, time  3 am/pm), enter Stop to terminate"
            );

            // Sends the user's message to the server as one text line.
            out.println(userMessage);

            // Waits for and reads one complete response line from the server.
            String serverReply = in.readLine();

            // Displays the response received from the server.
            System.out.println("Server Reply: " + serverReply);

            // Closes the client socket and releases the network connection and related resources.
            socket.close();

        } catch (IOException e) {
            // Prints a readable description of the error.
            System.out.println("Client error: " + e.getMessage());
        } finally {

            try {
                System.out.println("Closing Connection: ");
                socket.close();
            } catch (IOException) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
}
// Declares the EchoClient class.

public class MathClient {

    // Defines the main method, which is the starting point of the client program.
    public static void main(String[] args) {

        // Stores the server IP address that the client will try to contact; replace it if the server uses a different address.
        String host = "localhost";
        // Stores the server TCP port; it must match the port used by EchoServer.
        int port = 6000;

        // Starts a try block so networking and input/output errors can be handled safely.
        try {
            // Creates a TCP socket and immediately attempts to connect to the server identified by host and port.
            Socket socket = new Socket(host, port);
            // Prints confirmation after the TCP connection is successfully established.
            System.out.println("Connected to server.");

            // Creates a BufferedReader for efficiently reading text sent by the server.
            BufferedReader reader = new BufferedReader(
                    // Converts the socket's incoming byte stream into characters that BufferedReader can process.
                    new InputStreamReader(socket.getInputStream()));
            // Creates a PrintWriter for sending text to the server; true enables automatic flushing after println().
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Opens a dialog box and stores the text entered by the user in userMessage.
            String userMessage = JOptionPane.showInputDialog("Enter an operator(ADD SUB MUL DIV) and two numbers e.g MUL 5 8 or EXIT to close:");
            // Sends the user's message to the server as one text line.
            writer.println(userMessage);

            // Waits for and reads one complete response line from the server.
            String serverReply = reader.readLine();
            // Displays the response received from the server.
            System.out.println("Reply from server: " + serverReply);

            // Closes the client socket and releases the network connection and related resources.
            socket.close();

            // Catches input/output errors such as an unreachable server, refused connection, or stream failure.
        } catch (IOException e) {
            // Prints a readable description of the error.
            System.out.println("Client error: " + e.getMessage());
            // Ends the catch block.
        }
        // Ends the main method.
    }
// Ends the EchoClient class.
}

public class TCPEchoClient {

    private static InetAddress host;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        run();
    }

    private static void run() {
        Socket link = null;				//Step 1.
        try {
            link = new Socket(host, PORT);		//Step 1.
            //link = new Socket( "192.168.0.59", PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));//Step 2.
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);	 //Step 2.

            //Set up stream for keyboard entry...
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String message = null;
            String response = null;

            System.out.println("Enter message to be sent to server: ");
            message = userEntry.readLine();
            out.println(message); 		//Step 3.
            response = in.readLine();		//Step 3.
            System.out.println("\nSERVER RESPONSE> " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close();				//Step 4.
            } catch (IOException e) {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
    } // finish run method
}  //finish the class
