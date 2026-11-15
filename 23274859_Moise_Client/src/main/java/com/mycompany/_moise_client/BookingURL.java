/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_client;

/**
 *
 * @author moise
 */
public class BookingURL {
    
}

//
//public class URLConnection_Example {
//
//    /**
//     * @param args the command line arguments
//     */
//  public static void main(String[] args) throws IOException {
//        // TODO code application logic here
//     String urlLink ="http://www.google.com";
//     try 
//    {    
//      URL url = new URL( urlLink);
//
//      URLConnection c = url.openConnection();  //A URLConnection instance is obtained 
//    
//       // retrieve the communication protocol 
//       System.out.println("Protocol:- " + url.getProtocol()); 
//  
//        // retrieve the hostname of the url 
//        System.out.println("Hostname:- " + url.getHost()); 
//  
//        // retrieve the defalut port 
//        System.out.println("Default port:- " +  url.getDefaultPort()); 
//        
//        // retrive the path of URL 
//        System.out.println("Path:- " + url.getPath()); 
//  
//        // retrive the file name 
//        System.out.println("File:- " + url.getFile()); 
//      
//        
//        // retrive the fields part of the request message header section
//        for (int i=0; ; i++) 
//        {
//            String name = c.getHeaderFieldKey(i);  // reads the key of the n-th header field.
//            String value = c.getHeaderField(i);  //reads the value of the n-th header field.
//    
//            if (name == null && value == null)     // end of headers
//            {
//              break;         
//            }
//
//            if (name == null)     // first line of headers
//            {
//              System.out.println("Server HTTP version, Response code:");
//              System.out.println(value);
//              System.out.print("\n");
//              // use it to break down the response header
//              StringTokenizer st = new StringTokenizer(value," ");
//              String protocolVersion = st.nextToken();
//              System.out.println("Protocol Version = "+protocolVersion);
//              String statusCode = st.nextToken();
//              System.out.println("HTTP Status codes = "+statusCode);
//              System.out.println("\n");
//              
//              int intCode = new Integer(statusCode).intValue();
//              
//              if(intCode==200){
//                  System.out.println("Yes-URL exists");
//                  //move to next token 
//                  break;
//                  
//              }else if(intCode==404){
//                  System.out.println("No-URL does not exit!");
//              
//              }
//              
//              
//            }
//            else
//            {
//              //System.out.println(name + "=" + value);
//            }
//       }
//    } 
//    
//    catch (MalformedURLException e1) 
//    {
//        System.out.println ("URL error");
//        System.out.println(e1.getMessage());
//    } 
//    
//    catch (IOException e2) {
//        System.out.println ("IO Exception");
//        System.out.println(e2.getMessage());
//    }
//     
//  }
//}


