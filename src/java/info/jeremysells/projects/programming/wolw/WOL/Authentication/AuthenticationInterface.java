/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.WOL.Authentication;

import info.jeremysells.projects.programming.wolw.common.Conf;
import info.jeremysells.projects.programming.wolw.common.JxmlReader;


/**
 *
 * @author Jeremy Sells
 */
public class AuthenticationInterface {
    /**
     * Check if a user is able to acces the system
     * @param name
     * @param password
     * @return
     */
    public boolean authenticateUser(String username, String password) throws Exception
    {
        //Fix querying empty password
        if(password.equals(""))
            return false;

        JxmlReader reader = new JxmlReader(new Conf().passwordsXMLfileLocation);
        //Grab the correct password for the username supplied
        String correctPassword = reader.runSingleStringXPATH("/users/user[@name=\""+ username +"\"]/@password");
        return correctPassword.equals(password);
    }
}
