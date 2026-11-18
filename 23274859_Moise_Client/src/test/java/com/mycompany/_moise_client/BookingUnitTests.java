/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany._moise_client;

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
    public BookingUnitTests() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of main method, of class BookingClient.
     */
    @Test
    public void test3ConcurrentClientRun() {
        System.out.println("main");
        String[] args = null;
        BookingClient.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
