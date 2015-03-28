/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Jeremiah Garwick, John Zank, Mark Robinson, Courtenay Wheeler 
 * 
 * JavaClient creates threads that open a socket to the server and have the server execute 
 * commands and return results to the client while the client tracks the average access time it
 * took to access the server and returned a result.
 */

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.concurrent.atomic.AtomicLong;


public class ClientThread extends Thread{
    
    String menuSelect;
    
    Socket socket = null;
    AtomicLong ttime;
    AtomicLong runningThreads;
    boolean printOut;
    long startTime;
    long endTime;
    String hostname;
    ReceiveMessageInterface theInterface;

ClientThread(String hostN, String mItem, AtomicLong t, boolean p,AtomicLong rt, ReceiveMessageInterface myInterf)
    {
        menuSelect = mItem;
        ttime = t;
        printOut = p;
        runningThreads = rt;
	this.theInterface = myInterf;
    }   

public void run(){

	
        startTime = System.currentTimeMillis();
   try {


       System.out.println(theInterface.clientComm(menuSelect));
   }
   catch (Exception e) {
	e.printStackTrace();
        System.out.print("Could Not Connect with RMI *Error* ");

   }
          //gets end time from server
        endTime = System.currentTimeMillis();
        
	// endTime - startTime = the time it took to get the response from the sever
	ttime.addAndGet(endTime - startTime);

                runningThreads.decrementAndGet();


    }

    }
