/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.pcShutdown;

import info.jeremysells.projects.programming.wolw.common.APC;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is a tread which tell the pc to shutdown
 *
 * @author Jeremy Sells
 */
public class ShutdownPCThread extends Thread{

    private APC pc = null;
    private int port = 500;
    private String cmd = "Shutdown";

    /**
     * Initializer
     * @param apc
     * @param serverPort
     * @param commandToWrite
     */
    public ShutdownPCThread(APC apc, int serverPort, String commandToWrite)
    {
        pc = apc;
        port = serverPort;
        cmd = commandToWrite;
    }



    public void run()
    {
        String pcAddress = "";

        //If have IP address
        if(!pc.ipAddress.equals(""))
        { pcAddress = pc.ipAddress; }
        else //Else use hostname
        { pcAddress = pc.hostname; }
            

        //Do the sending
        Socket socket = null;
        PrintWriter writer = null;

        try{
            socket = new Socket(pcAddress, port);
            writer = new PrintWriter(socket.getOutputStream());
            writer.write(cmd);
            }
        catch(Exception e)
        {
        }
        finally
        {
            if(writer != null)
            { try{writer.close();}catch(Exception e){} }
            if(socket != null)
            { try{socket.close();}catch(Exception e){} }
        }

    }


}
