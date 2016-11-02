/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.pcStatus;

import info.jeremysells.projects.programming.wolw.common.APC;
import info.jeremysells.projects.programming.wolw.common.Conf;
import java.net.InetAddress;

/**
 *
 * @author Jeremy Sells
 */
public class StatusInterface {

    /**
     * Checks if a pc is online
     * @param pc
     * @return
     * @throws Exception
     */
    public static Boolean isPCOnline(APC pc) throws Exception
    {


        //If the ip address exists
        if(!(pc.ipAddress.equals("") || (pc.ipAddress == null)))
        {
            InetAddress address = InetAddress.getByName(pc.ipAddress);
            if(address.isReachable(Conf.pcLookupTimeOut))
            { return true; }
        }//Else us hostname
        if(!(pc.hostname.equals("") || (pc.hostname == null)))
        {
            try
            {
                InetAddress address = InetAddress.getByName(pc.hostname);
                if( address.isReachable(Conf.pcLookupTimeOut))
                { return true; }
            }
            catch(Exception e)
            {
                
            }

        }


        return false;
    }
}
