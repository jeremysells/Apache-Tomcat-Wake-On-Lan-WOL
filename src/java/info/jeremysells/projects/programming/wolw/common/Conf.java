/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.common;

import java.net.URL;

/**
 *
 * @author Jeremy Sells
 */
public class Conf {
    public final URL wolPClistXMLfileLocation = getClass().getResource(".info.jeremysells.projects.programming.wolw.pcList".replace(".", "/") + "/pcControlList.xml");
    public final URL passwordsXMLfileLocation = getClass().getResource(".info.jeremysells.projects.programming.wolw.WOL.Authentication".replace(".", "/") + "/AccessList.xml");
    public static final int pcLookupTimeOut = 4000;
}
