
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.concurrent.atomic.*;
import java.util.*;



/**
 * 
 * @author Jeremiah Garwick, John Zank, Mark Robinson, Courtenay Wheeler 
 * 
 * JavaClient creates threads that open a socket to the server and have the server execute 
 * commands and return results to the client while the client tracks the average access time it 
 * took to access the server and returned a result.
 */
public class JavaClient{

	//set host/thread/time variables
    //Create a Linklist to create the threads
	private static Thread threading = null;
	private static String Host;
	private static AtomicLong ttime = new AtomicLong(0);
	private static AtomicLong rThreads = new AtomicLong(0);
	private static LinkedList<Thread> theList = new LinkedList<Thread>();
	private static boolean print = true;

    /**
    *
    * @param args hostname  - user enters Ip address of the server at the start of running the program
    */
    public static void main(String[] args)
	{
        //instance input and set to zero and set processes to 1 as default
        int input = 0;
        int processes = 1;
        String menuInput = null;
        System.setSecurityManager(new RMISecurityManager());
        String strName = args[0];
        System.out.println("Client: Looking up " +strName+ "...");
        ReceiveMessageInterface theInterf = null;

        try{
            theInterf = (ReceiveMessageInterface)Naming.lookup(strName);
	System.out.println(theInterf + " should not benull");
	
        }
        catch (Exception e){
		e.printStackTrace();
            System.out.println("Client: Exception thrown looking up " +strName);
        }
        if (args.length == 0){
            System.out.println("Hostname not Entered");
            System.exit(0);
            }

        Scanner scan = new Scanner(System.in);


        //creates a menu and loops until the user selects to end the program
        while (input < 7) {

            System.out.println("The menu provides the following choices to the user: ");
            System.out.println("1. Host current Date and Time " );
            System.out.println("2. Host Uptime");
            System.out.println("3. Host memory use \n4. Host Netstat \n5. Host current users ");
            System.out.println("6. Host running processes");
            System.out.println("7. Quit ");
            System.out.println();
            System.out.print("Please provide number corresponding to the action you want to be performed: ");
                    int menuselect = scan.nextInt();
                    //checks if not exiting and asks user for how many Clients to create
                    if(menuselect != 7)
                        {
                        System.out.println(" How many Clients? ");
                        processes =  scan.nextInt();

                        }

                    //switch changes  menuInput to the command the user desires to run on the server
                    switch(menuselect)
            {
            case 1:
                menuInput = "date";
                break;
            case 2:
                menuInput = "uptime";
                break;
            case 3:
                menuInput = "free";
                break;
            case 4:
                menuInput = "netstat";
                break;
            case 5:
                menuInput = "who";
                break;
            case 6:
                menuInput = "ps -e";
                break;
            case 7:
                            System.out.println("Exiting Program");
                System.exit(0);
                break;

            }
            // setting ttime to zero to start counting access time and also set rThreads
            //to the number of processes to run
            ttime.set(0);
            rThreads.set(processes);

                    //Loop until all the threads required have been created
            for(int x = 0; x < processes; x++)
            {
                    //creates a new client thread and passes the Command for the server,  the total time,
                    // whether to print the input recieved from the server ,  and the total number of threads
                    // that are created.
            threading = new ClientThread(args[0], menuInput, ttime, print,rThreads,theInterf);
                    //runs the run method of the ClientThread
            threading.start();
                    //adds the thread to a LinkedList
            theList.add(threading);

            }
            //loops for every Client opened
            for(int x = 0;x < processes;x++)
            {
                try{
                                //This waits for the thread to die then continues..
                    theList.get(x).join();
                }
                catch (InterruptedException ex){
                    System.out.print("error in thread");

                }
            }
            //rThreads is decremented after each socket that is open is closed
                    //the while loops until the number of processes running is 0 and all the processes
                    // have closed
            while(rThreads.get() !=0)
            {}

                    //Displays the average access times for all the processes run.
            System.out.println("Average Response time "+ (ttime.get()/processes) + "ms");

            System.out.println();
            // resets processes to 1 and set print to true.
                    processes = 1;
            print = true;


            }


        }
}
