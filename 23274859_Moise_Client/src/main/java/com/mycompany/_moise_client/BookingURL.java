/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author moise
 */




public class BookingURL {

    public static void HTTPGETBookings(String urlLink, PrintWriter out, BufferedReader in) {

        //counters for imported and skipped lines
        int imported = 0;
        int skipped = 0;
        //declare a URL connection  that can be closed in final
        URLConnection c = null;
        //reads text file hosted in the s3 bucket online
        BufferedReader reader = null;
        try {

            //creates a url
            URL url = new URL(urlLink);

            //connection instance is obtained
            c = url.openConnection();

            //reads the text file in the s3 bucket online
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));

            //retrieve the first line of the text file
            String firstLine = reader.readLine();

            //check first line start with members and as a name, and after
            if (firstLine == null || !firstLine.startsWith("member=") || firstLine.substring(7).trim().isEmpty()) {
                System.out.println("Import failed: Member or name is missing");
                //stop importing
                return;
            }

            //extract name from substring taken after =
            String memberName = firstLine.substring(7).trim();

            //store each booking
            String line;

            //continue reading until the end of the text file
            while ((line = reader.readLine()) != null) {
                //skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                //-1 ensures trailing empty string is preserved
                String[] parts = line.trim().split(";", -1);

                //check the number of fields the booking as
                if (parts.length != 3) {
                    System.out.println("Malformed line: " + line + " Booking does not have required number of fields");
                    skipped++;
                    //skip the line
                    continue;

                }

                //remove unecessary spaces from each field
                String workshop = parts[0].trim();
                String date = parts[1].trim();
                String time = parts[2].trim();

                if (workshop.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    System.out.println("Malformed line: " + line + " Booking must have workshop, date, time.");
                    skipped++;
                    continue;
                }

                //construct the normal booking command expected by ProcessAction
                String command = "Book; " + memberName + ";" + workshop + "; " + date + ";" + time;

                //send booking through existing TCP connection
                out.println(command);

                //server reply to process
                String serverReply = in.readLine();

                //count successful bookings
                if (serverReply == null) {
                    System.out.println("Server disconnected unexpectedly during import");
                    break;
                }

                //print the server reply
                System.out.println("Server Reply: " + serverReply);
                if (serverReply.startsWith("InvalidCommandException")) {
                    skipped++;
                } else {
                    imported++;
                }
            }

        } catch (MalformedURLException e1) {
            System.out.println("URL error:\n" + (e1.getMessage()));

        } catch (IOException e2) {
            System.out.println("IO error:\n" + e2.getMessage());
        } finally {
            //close the reader
            if (reader != null) {

                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("");
                }
            }
            
            System.out.println("Imported: "+imported+"; Skipped: "+skipped);
        }
    }
}
