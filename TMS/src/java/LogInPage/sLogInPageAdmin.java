/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package LogInPage;

import beans.LogInfo;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import logIn.LogInManager;

/**
 *
 * @author Abree
 */
public class sLogInPageAdmin extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private LogInfo _MyUser = null;
    private LogInManager _MySqlHelper = new LogInManager();
    private boolean isValidUser = false;
    
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
    if(isValidUser) {
        response.sendRedirect("/TMS/webpages/Admin/AdminSelection.jsp");
        _MyUser.setDisplay(" ");
    }else{
        _MyUser.setDisplay("Invalid Credential.");
        response.sendRedirect("/TMS/webpages/Admin/AdminMain.jsp");
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
    _MyUser = (LogInfo)request.getSession().getAttribute("LogUserInfoAdmin");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyUser.setUsername(request.getParameter("txtUserName").trim());
    _MyUser.setPassword(request.getParameter("txtPassword").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_ButtonClicked.compareToIgnoreCase("Sign In") == 0) {
        isValidUser = _MySqlHelper.isValidAdminUser(_MyUser);
        if(isValidUser) {
            _MySqlHelper.setActions(_MyUser);
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
