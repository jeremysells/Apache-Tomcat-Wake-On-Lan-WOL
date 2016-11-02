/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.servlets;

import info.jeremysells.projects.programming.wolw.WOL.Authentication.AuthenticationInterface;
import info.jeremysells.projects.programming.wolw.WOL.InterfaceWOL;
import info.jeremysells.projects.programming.wolw.common.APC;
import info.jeremysells.projects.programming.wolw.pcList.pcListInterface;
import info.jeremysells.projects.programming.wolw.pcShutdown.ShutdownPCInterface;
import info.jeremysells.projects.programming.wolw.pcStatus.StatusInterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jeremy Sells
 */
public class WakePC extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>WOLW - Wake On Lan Website</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>WOLW - Wake On Lan Website</h1>");

        try {
            if(!isAuthenticated(request))   //Check authentication
            {
                //out.println("Sorry, Incorrect credentials. Please try again.");
                insertAuthentication( out);
            }
            else
            {
                checkForPostTasks(request,out);
                printHTMLcontrolsEGwakeForm(request, out);
            }
        }
        catch(Exception E)
        {
            out.println("<br />Error: " + E);
        }
        finally
        {
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }

    /**
     * Does the checking for post tasks (e.g. from forms)
     * And passwords later on
     * @param request
     */
    private void checkForPostTasks(HttpServletRequest request,PrintWriter out) throws Exception
    {
        ArrayList<APC> pcs = new pcListInterface().getAlladdresses();

        //Check for WOL on in Post
        if(request.getParameter("WakePC") != null)
        {
            if(!request.getParameter("WakePC").equals("null"))
            {
                int pcNumToWOL = Integer.parseInt(request.getParameter("WakePC"));
                out.println("Just work up : " + pcs.get(pcNumToWOL).name + ", MAC : " +pcs.get(pcNumToWOL).macAddress +" <br/>");
                new InterfaceWOL().runWOL(pcs.get(pcNumToWOL));
            }
        }

        //Check for shutdown commands
        if(request.getParameter("ShutdownPC") != null)
        {
            if(!request.getParameter("ShutdownPC").equals("null"))
            {
                int pcNumToShutdown = Integer.parseInt(request.getParameter("ShutdownPC"));
                out.println("Just shutdown : " + pcs.get(pcNumToShutdown).name + "<br/>");
                new ShutdownPCInterface().shutdownPC(pcs.get(pcNumToShutdown));
            }
        }
    }

    /**
     * Make the form (HTML) to wake up a PC
     */
    private void printHTMLcontrolsEGwakeForm(HttpServletRequest request,PrintWriter out) throws Exception
    {
    out.println("<h4>WOLW - Wake On Lan Web app</h4>");

    //Refresh button
    out.println("<form action=\"WakePC\" method=\"post\"/>");
    printConsistentFormData(request, out);
    out.println("<input type=\"submit\" value=\"" + "Refresh" + "\"></input>");
    out.println("</form> ");



    out.println("<table border=\"1\">");
    out.println("<tr><td>Name</td><td>Status</td><td>Wake PC</td><td>shutdown PC</td></tr>");

    //Print out the list of pcs that can be WOLed
    ArrayList<APC> pcs = new pcListInterface().getAlladdresses();
    for(int i = 0; i != pcs.size(); i++)
    {
        APC currentPC = pcs.get(i);

        out.println("<tr>");
            //Name Cell
            out.println("<td>");
            out.println(currentPC.name);
            out.println("</td>");


            //Status Cell
            out.println("<td align=\"center\">");
                if(StatusInterface.isPCOnline(currentPC))
                { out.println("<img src=\""+ "icons/ON.jpg" +"\" alt=\"PC online\" />"); }
                else
                { out.println("<img src=\""+ "icons/OFF.jpg" +"\" alt=\"PC online\" />"); }
            
            out.println("</td>");

            //Wake up Cell
            out.println("<td align=\"center\">");
                out.println("<form action=\"WakePC\" method=\"post\"/>");
                out.println("<input type=\"hidden\" name=\"WakePC\" value=\""+ Integer.toString(i) +"\" />");
                printConsistentFormData(request, out);
                out.println("<input type=\"submit\" value=\"" + "WOL" + "\"></input>");
                out.println("</form> ");
            out.println("</td>");
            
            //Shutdown Cell
            out.println("<td>");
                out.println("<form action=\"WakePC\" method=\"post\"/>");
                out.println("<input type=\"hidden\" name=\"ShutdownPC\" value=\""+ Integer.toString(i) +"\" />");
                printConsistentFormData(request, out);
                out.println("<input type=\"submit\" value=\"" + "Shutdown" + "\"></input>");
                out.println("</form> ");
            out.println("</td>");
            
        out.println("</tr>");
    }
    out.println("</table>");
    }

    /**
     * For consistency, reprints
     * @param request
     * @param out
     */
    private void printConsistentFormData(HttpServletRequest request, PrintWriter out)
    {
      //Insert username and password
        ArrayList<String> ignoreKeys = new ArrayList<String>();
        ignoreKeys.add("WakePC");
        ignoreKeys.add("ShutdownPC");
        printConsistenPOSTdata(request, out, ignoreKeys);
    }



    /**
     * For consistency, reprints
     * @param request
     * @param out
     */
    private void printConsistenPOSTdata(HttpServletRequest request, PrintWriter out, ArrayList<String> ignoreKeys)
    {
        Set allParametersSet = request.getParameterMap().keySet();
        for (Iterator i = allParametersSet.iterator(); i.hasNext();) {
            String currentKey = (String) i.next();
            String currentValue = request.getParameter(currentKey);
            if(!ignoreKeys.contains(currentKey))
                out.println("<input type=\"hidden\" name=\"" + currentKey + "\" value=\"" + currentValue + "\"/>" );
        }
    }


    /**
     * This does the security checking
     * @param request
     * @param response
     * @param out
     */
    private boolean isAuthenticated(HttpServletRequest request) throws Exception
    {
       if(request.getParameter("password") == null)
           return false;
       if(request.getParameter("username") == null)
           return false;

       return new AuthenticationInterface().authenticateUser(request.getParameter("username"), request.getParameter("password"));
    }




    /**
     * Prints out the authentication
     * @param request
     * @param response
     * @param out
     */
    private void insertAuthentication(PrintWriter out)
    {
        out.println("<br/> Please enter Username and Password");
        out.println("<br/><form name=\"input\" action=\"WakePC\" method=\"post\">");
        out.println("<br/>Username: <input type=\"text\" name=\"username\" />");
        out.println("<br/>Password: <input type=\"password\" name=\"password\" />");
        out.println("<br/><input type=\"submit\" value=\"Submit\" />");
        out.println("<br/></form>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



}
