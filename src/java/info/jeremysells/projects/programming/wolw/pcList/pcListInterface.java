/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.pcList;

import info.jeremysells.projects.programming.wolw.common.JxmlReader;
import info.jeremysells.projects.programming.wolw.common.APC;
import info.jeremysells.projects.programming.wolw.common.Conf;
import java.util.ArrayList;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jeremy Sells
 */
public class pcListInterface {

    /**
     * Gets a list of PC's to turn on from the xml file in the Conf
     * @return
     */
    public ArrayList<APC> getAlladdresses() throws Exception
    {
        ArrayList<APC> allPCs = new ArrayList<APC>();

        JxmlReader pcXMLReader = new JxmlReader(new Conf().wolPClistXMLfileLocation);

        //Read each pcs Nick name and get the mac address from the file
        NodeList allPCNickNames = pcXMLReader.runMultipleReXPATH("/pcList/apc/@nickName");
        for(int i = 0; i != allPCNickNames.getLength(); i++)
        {
            //Make a new pc
            APC newlyReadPC = new APC();
            newlyReadPC.name = allPCNickNames.item(i).getTextContent();
            newlyReadPC.macAddress = pcXMLReader.runSingleStringXPATH("/pcList/apc[@nickName=\""+ newlyReadPC.name +"\"]/@mac");
            newlyReadPC.ipAddress = pcXMLReader.runSingleStringXPATH("/pcList/apc[@nickName=\""+ newlyReadPC.name +"\"]/@ip");
            newlyReadPC.hostname = pcXMLReader.runSingleStringXPATH("/pcList/apc[@nickName=\""+ newlyReadPC.name +"\"]/@hostName");

            //Add to list
            allPCs.add(newlyReadPC);
        }



        return allPCs;
    }
}
