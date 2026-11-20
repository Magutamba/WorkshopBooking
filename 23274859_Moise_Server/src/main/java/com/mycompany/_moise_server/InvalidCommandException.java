/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany._moise_server;

/**
 *
 * @author moise
 */
 //invalid command custom class to handle malfourmed data and invalid commands
public class InvalidCommandException extends Exception {

    public InvalidCommandException(String error){
         //pass the error message to exception
         //exception stores it internally and getMessage() retrieves it
        super(error);
    }
}
