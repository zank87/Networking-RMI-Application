import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class Server extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {
    // IP address and registry for RMI
    String address;
    Registry registry;
    AtomicInteger numThreads = new AtomicInteger(0);
    // The list of threads is kept in a linked list
    ArrayList<Thread> list = new ArrayList<Thread>();

    // Processes commands from client via the RMI interface
    public String clientComm(String comm) throws RemoteException {
        ServerThread thrd = new ServerThread(comm);
        list.add(thrd);
        numThreads.incrementAndGet();
        System.out.println("Thread " + numThreads.get() + " started.");
        return thrd.process();
        // Return the output of the command and terminating thread
    }

    // Server constructor
    public Server() throws RemoteException{
        try {
            // Sets address of server to local hosts address
            address = (InetAddress.getLocalHost().toString());
        }
        catch (Exception e){
            System.out.println("Can't get INet address.");
            e.printStackTrace();
        }
        // designating a port number
        int port = 1099;
        try{
            registry = LocateRegistry.createRegistry(port);
            registry.rebind("rmiServer", this);
        }catch (RemoteException e){
            System.out.println("e");
        }
    }

    public static void main(String[] args) {
        try{
            String rmiServerName = "RMI_SERVER";
            System.out.println("Server: Registering as " +rmiServerName);
            Server rmiServer = new Server();
            Naming.rebind(rmiServerName, rmiServer);
            System.out.println("Server online.");
        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}


class ServerThread extends Thread {
    String client = null;

    public ServerThread(String client) {
        this.client = client;
    }

    public String process() {
        System.out.print("Accepted client. ");

        String inString = client;
        // read the command from the client
        System.out.println("Read command " + inString);
        // run the command using InputCommand and get its output
        String outString = termComm(inString);
        System.out.println("Server sending result to client");
        // send the result of the command to the client
        return outString;
    }

    static String termComm(String termComm) {
        String result = "";
        String line;
        try {
            // start the shell command running as a child processes
            Process child = Runtime.getRuntime().exec(termComm);

            // open a BufferedReader to read the output of the child process
            BufferedReader output = new BufferedReader(new InputStreamReader(child.getInputStream()));
            // while the child process is still outputting, add the output to the result string
            while ((line = output.readLine()) != null) {
                result = result.concat(line);
                result = result.concat("\n");
            }

            result = result.concat("\n");
            result = result.concat("Output Complete");
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}


