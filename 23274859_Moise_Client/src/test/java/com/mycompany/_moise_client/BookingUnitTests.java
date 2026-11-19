/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany._moise_client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author moise
 */
public class BookingUnitTests {

    // Stores the server IP address that the client will try to contact; replace it if the server uses a different address.
    private static String host = "localhost";
    // Stores the server TCP port; it must match the port used by EchoServer.
    private static int port = 1234;
//    public BookingUnitTests() {
//    }

    @Before
    public void setUp() {

    }

    // helper method to run three clients concurrently
    public String[] runConcurrentClients(String[] commands) throws Exception {
        //create a thread pool, to handle each of the three clients
        //ExecutorService is used to symplify thread management, write less code and increase efficiency
        //reuse existing threads instead of creating new ones
        ExecutorService executor = Executors.newFixedThreadPool(3);

        //synchronization aid to make sure all three clients connect before sending commands
        CountDownLatch ready = new CountDownLatch(3);

        //clients wait until main test releases them
        CountDownLatch start = new CountDownLatch(1);

        //Future provides a way to store the result of different threads and efficiently catch excepttions instead of fire and forget
        List<Future<String>> results = new ArrayList<>();

        try {
            //submits three client tasks
            for (int i = 0; i < 3; i++) {
                //each task receives its own command
                final String command = commands[i];

                //submit the client tasks to the executor service
                Future<String> result = executor.submit(() -> {
                    //declare a socket and intialise to null
                    Socket socket = null;
                    //each client establishes its own TCP connection
                    try {
                        // Creates a TCP socket and immediately attempts to connect to the server identified by host and port.
                        socket = new Socket(host, port);

                        //BufferedReader for efficiently reading text sent by the server.
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        //PrintWriter for sending text to the server; true enables automatic flushing after println().
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        //inform the main test that this client is ready
                        ready.countDown();

                        //wait until all clients three clients are ready
                        start.await();

                        // Sends the clients command to the server
                        out.println(command);

                        //read and return the server response
                        return in.readLine();

                    } finally {
                        //close socket after receiving reply
                        if (socket != null) {
                            socket.close();
                        }
                    }

                });
                //store the Future for retrieving the response later
                results.add(result);
            }
            //wait until all three clients have connected , test fails if they do not connect within 10 seconds
            assertTrue(ready.await(10, TimeUnit.SECONDS));

            //release all three clients to send their commands
            start.countDown();

            //array to store the three server responses
            String[] replies = new String[3];

            //retrieve each client's response
            for (int i = 0; i < 3; i++) {
                //get() waits until the task finishes, timeout prevents unlimited wait

                replies[i] = results.get(i).get(10, TimeUnit.SECONDS);
                //display the response
                System.out.println("Client" + (i + 1) + "Server Reply: " + replies[i]);

                //the server never actually returned a response
                assertNotNull(replies[i]);

            }
            return replies;

        } finally {
            //release any clients still waiting at the start latch
            start.countDown();
            //stop the executor and clean up worker threads
            executor.shutdownNow();
        }
    }

    //test for three clients in one concurrent run
    @Test
    public void oneConcurrentRunThreeClients() throws Exception {
        //test client name  array
        String[] memberNames = {"Chancel", "Veronique", "Bryson"};

        String[] commands = {
            //client 1 valid input
            "book;" + memberNames[0] + ";Advanced Business Computing; 11 April 2026; 3 pm",
            //client 2 malformed booking
            "book;" + memberNames[1] + ";Enterprise Computing Beginer; 8 am",
            //client 3 invalid action
            "convert;" + memberNames[2] + ";Cloud Web Applications; 7 september 2021; 9 AM"
        };
        //get the replies for the       
        String[] replies = runConcurrentClients(commands);

        //client 1 successfully creates a booking
        assertFalse(replies[0].contains("InvalidCommandException:"));
        assertTrue(replies[0].contains("Advanced Business Computing"));
        assertTrue(replies[0].contains(memberNames[0]));

        //client 2 booking fails due to date not being provided
        assertTrue(replies[1].contains("InvalidCommandException:"));
        assertTrue(replies[1].contains("Date as to be provided"));

        //client 3 fails due to an invalid action being used
        assertTrue(replies[2].contains("InvalidCommandException:"));
        assertTrue(replies[2].contains("Not a valid action e.g(book, cancel, list)"));

        //
    }

    @Test
    public void HTTPGetBookingsTest() throws Exception {
        //url link to Bookings.ttxt in S3 bucket
        String urlLink = "https://bookingtxtget.s3.eu-west-1.amazonaws.com/Bookings.txt";
        String urlAction = "import;";
        Socket socket = null;
        try {
            // Creates a TCP socket and immediately attempts to connect to the server identified by host and port.
            socket = new Socket(host, port);

            //Prints confirmation after the TCP connection is successfully established.
            System.out.println("Connected to server.");

            //BufferedReader for efficiently reading text sent by the server.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //PrintWriter for sending text to the server; true enables automatic flushing after println().
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //call HTTP GET METHOD to retrieve imports
            BookingURL.HTTPGETBookings(urlLink, out, in);
            
            //check that the imported bookings reached the server

        }
    }

}
