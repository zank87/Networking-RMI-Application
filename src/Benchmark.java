
import java.util.concurrent.atomic.*;
import java.util.*;



/**
 * 
 * @author Jeremiah Garwick
 */
public class Benchmark {
	//set host/thread/time variables
	private static Thread threading = null;
	private static String Host;
	private static AtomicLong ttime = new AtomicLong(0);
	
	
	private static AtomicLong rThreads = new AtomicLong(0);
	
	private static LinkedList<Thread> theList = new LinkedList<Thread>();
	
	private static boolean print = true;

/**
 * 
 * @param args hostname
 */
public static void main(String[] args)
	{
            
        int x = 0;
	int input = 0;
	int processes = 1;
	String menuInput = "date";
        
        
	
        print = false;
        
	ttime.set(0);
		rThreads.set(processes);
		for(x = 0; x < processes; x++)
		{
		threading = new ClientThread(args[0], menuInput, ttime, print,rThreads);
		threading.start();
		theList.add(threading);
		
		}
		
		for(x = 0;x < processes;x++)
		{
			try{
				theList.get(x).join();
			}
			catch (InterruptedException ex){
				System.out.print("error in thread");
				
			}
		}
		
		while(rThreads.get() !=0)
		{}
		
		System.out.println("For " +processes +"  . there is an Average Response time "+ (ttime.get()/processes) + "ms");
		System.out.println();
		processes = 1;
		
		
                
		

	
	for(x = 5; x<51; x = x + 5) 
        {
               menuInput = "date";
               
               processes = x;
               print = false;     
                
		
		
		ttime.set(0);
		rThreads.set(processes);
		for( x = 0; x < processes; x++)
		{
		threading = new ClientThread(args[0], menuInput, ttime, print,rThreads);
		threading.start();
		theList.add(threading);
		
		}
		
		for(x = 0;x < processes;x++)
		{
			try{
				theList.get(x).join();
			}
			catch (InterruptedException ex){
				System.out.print("error in thread");
				
			}
		}
		
		while(rThreads.get() !=0)
		{}
		
		System.out.println("For " +processes +"  . there is an Average Response time "+ (ttime.get()/processes) + "ms");
		System.out.println();
		processes = 1;
		
		
	
		}

        /*
        
        * This next set of codes runs a netstat banchmark  
        */
        
        System.out.println("Now here are Netstat Benchmark Results");
         menuInput = "netstat";
        print = false;
        processes = 1;
	ttime.set(0);
		rThreads.set(processes);
		for(x = 0; x < processes; x++)
		{
		threading = new ClientThread(args[0], menuInput, ttime, print,rThreads);
		threading.start();
		theList.add(threading);
		
		}
		
		for(x = 0;x < processes;x++)
		{
			try{
				theList.get(x).join();
			}
			catch (InterruptedException ex){
				System.out.print("error in thread");
				
			}
		}
		
		while(rThreads.get() !=0)
		{}
		
		System.out.println("For " +processes +"  . there is an Average Response time "+ (ttime.get()/processes) + "ms");
		System.out.println();
		processes = 1;
		
		
                
		

	
	for(x = 5; x<51; x = x + 5) 
        {
               menuInput = "date";
               
               processes = x;
               print = false;     
                
		
		
		ttime.set(0);
		rThreads.set(processes);
		for( x = 0; x < processes; x++)
		{
		threading = new ClientThread(args[0], menuInput, ttime, print,rThreads);
		threading.start();
		theList.add(threading);
		
		}
		
		for(x = 0;x < processes;x++)
		{
			try{
				theList.get(x).join();
			}
			catch (InterruptedException ex){
				System.out.print("error in thread");
				
			}
		}
		
		while(rThreads.get() !=0)
		{}
		
		System.out.println("For " +processes +"  . there is an Average Response time "+ (ttime.get()/processes) + "ms");
		System.out.println();
		processes = 1;
		
		
	
		}


	}
}
