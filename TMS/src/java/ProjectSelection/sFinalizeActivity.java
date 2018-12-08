/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ProjectSelection;

import beans.LogInfo;
import java.io.*;
import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import message.ErrorMessage;
import tmsclass.ProjectListing.TransactionWasNotSavedException;
import tmsclass.ProjectListing.sqlHelper;

/**
 *
 * @author Abree
 */
public class sFinalizeActivity extends HttpServlet {
    private String _ButtonClicked = " ";
    private String[] _MyCheckBox = {""};
    private LogInfo _MyLogInfo = null;
    private ErrorMessage _MyError = null;
    private sqlHelper _MySqlHelper = new sqlHelper();
    
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
    response.sendRedirect("/TMS/webpages/TimeLog/ProjectSelection.jsp#" + _ButtonClicked);
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
    _ButtonClicked = request.getParameter("glbutton");
    _MyLogInfo = (LogInfo)request.getSession().getAttribute("LogUserInfo");
    _MyError = (ErrorMessage)request.getSession().getAttribute("MessageManager");
    
    if(request.getParameterValues("checkbox") == null) {
        for(int i = 0; i < _MyCheckBox.length; i++) _MyCheckBox[i] = " ";
        _MyCheckBox[0] ="XNe391JlT1o"; //Just set ActivityList to Something
    }else{
        _MyCheckBox = request.getParameterValues("checkbox");
    }
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Save") == 0) {
        try {
            _MySqlHelper.setStatus(_MyCheckBox, _MyLogInfo.getUserId());
        } catch (TransactionWasNotSavedException ex) {
            Logger.getLogger(sFinalizeActivity.class.getName()).log(Level.SEVERE, null, ex);
            //Transaction was not saved (%1)
            _MyError.setMessageNumber("10005");
            _MyError.addParameter("Change status of activity.");
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
