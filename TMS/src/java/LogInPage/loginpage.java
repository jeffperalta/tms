/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LogInPage;

import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

import beans.LogInfo;
import logIn.*;
import tmsclass.ProjectListing.TransactionWasNotSavedException;

/**
 *
 * @author jperalta
 */
public class loginpage extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private LogInfo _MyLogInfo = null;
    private LogInManager _MySqlHelper = new LogInManager();
    private boolean _isValidUser = false;
    
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
/******************************************************************************
 *--Response Section--                                                        *
 ******************************************************************************/       
            
            if(_ButtonClicked.compareToIgnoreCase("Sign In") == 0){
                if(_isValidUser) {
                    response.sendRedirect("/TMS/webpages/TimeLog/ProjectSelection.jsp");
                    _MyLogInfo.setDisplay(" ");
                }else{
                    _MyLogInfo.setDisplay("Invalid Credential.");
                    response.sendRedirect("/TMS/index.jsp");
                }
            }
            
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException { 
        
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
/******************************************************************************
 *--Declaration/Instantiation Section--                                       *
 ******************************************************************************/
    _ButtonClicked = request.getParameter("btnSubmit"); 
    _MyLogInfo = (LogInfo)request.getSession().getAttribute("LogUserInfo");
    _isValidUser = false;
    tmsclass.WorkUtility.sqlHelper myWorkUtilitySqlHelper = new tmsclass.WorkUtility.sqlHelper();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyLogInfo.setUsername(request.getParameter("txtUserName"));
    _MyLogInfo.setPassword(request.getParameter("txtPassword"));
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Sign In") == 0) {
        if(_MySqlHelper.isValid(_MyLogInfo)){
            _isValidUser = true;
            _MySqlHelper.setActions(_MyLogInfo);
            
           //Update the Deadline for all employees 
           try {
                myWorkUtilitySqlHelper.updateDateLine();
            } catch (TransactionWasNotSavedException ex) {
                Logger.getLogger(loginpage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
