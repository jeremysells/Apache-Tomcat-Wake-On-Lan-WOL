/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.pcShutdown;

import info.jeremysells.projects.programming.wolw.common.APC;

/**
 *
 * @author Jeremy Sells
 */
public class ShutdownPCInterface {
    public void shutdownPC(APC pc)
    {
        Thread thread = new ShutdownPCThread(pc, 500, "Shutdown pc now please");
        thread.start();
    }
}
