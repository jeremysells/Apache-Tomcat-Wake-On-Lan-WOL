/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.WOL;

import info.jeremysells.projects.programming.wolw.common.APC;

/**
 *
 * @author Jeremy Sells
 */
public class InterfaceWOL {

    /**
     * The interface
     * @param pcMacAddress
     */
    public void runWOL(APC pc) throws Exception
    {
       String pcMacAddress = pc.macAddress;
       securityCheck(pcMacAddress);

        //Works
        new WOL().runWOL(pcMacAddress, "192.168.0.255",9);

        //Others
        new WOL().runWOL(pcMacAddress, "255.255.255.255", 40000);
        new WOL().runWOL(pcMacAddress, "255.255.255.0",40000);

        new WOL().runWOL(pcMacAddress, "255.255.255.255",9);
        new WOL().runWOL(pcMacAddress, "255.255.255.0",9);

        new WOL().runWOL(pcMacAddress, "192.168.0.255",40000);

        new WOL().runWOL(pcMacAddress, "192.168.2.255",40000);
        
    }

    /**
     * Runs a security check.
     * //TODO: better error messages
     * @param pcMacAddress
     * @return
     */
    private void securityCheck(String pcMacAddress) throws Exception
    {
        if(pcMacAddress.length() != 17)
           throw new Exception("Error in calc pcMacAddress.length() != 17");

        if(countChars(pcMacAddress,'-') != 5)
            if(countChars(pcMacAddress,':') != 5)
                throw new Exception("Error in calc, countChars(pcMacAddress,'-') != 5 and countChars(pcMacAddress,':') != 5 failed");

        //See http://www.velocityreviews.com/forums/t92353-regular-expression.html for regex
        if(!pcMacAddress.matches("^[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}-[0-9A-F]{2}$"))
            if(!pcMacAddress.matches("^[0-9A-F]{2}:[0-9A-F]{2}:[0-9A-F]{2}:[0-9A-F]{2}:[0-9A-F]{2}:[0-9A-F]{2}$"))
            throw new Exception("Error in regular expression checking");

    }

    /**
    * Counts the number of one char in the string string
    * @param string
    * @param theChar
    * @return
    */
    private int countChars(String string, char theChar)
    {
        if(string.length() == 0)
        {
            return 0;
        }

        String newString = string.substring(1);
        if(string.charAt(0) == theChar)
        {
          return 1 + countChars(newString, theChar);
        }
        else
        {
          return countChars(newString, theChar);
        }
    }
}
