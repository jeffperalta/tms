/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package User;

import beans.PageNavigation;
import beans.User;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class sUserSearchResult extends HttpServlet {
   
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
    private User _MyUser = null;
    private PageNavigation _MyPageNavigation = null;
    
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
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BUserPageNav");
    response.sendRedirect(_MyPageNavigation.getFileName());
            
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
        
/******************************************************************************
 *--Declaration/Instantiation Section--                                       *
 ******************************************************************************/
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    _MyUser = (User)request.getSession().getAttribute("BUser");
            
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    
   FieldList.clear();
   ResultList.clear();
   
   FieldList.add("first_name");
   FieldList.add("middle_name");
   FieldList.add("Last_name");
   FieldList.add("Date_entered");
   FieldList.add("username");
   FieldList.add("pass");
   FieldList.add("active");
   FieldList.add("access_type");
   FieldList.add("contact_info");
   FieldList.add("dept_id");
   
   _MyUser.setUserID(request.getParameter("ID").trim());
   ResultList = _MyGenSqlHelper.getDataElement("a_users", 
                    FieldList, "user_id='" + _MyUser.getUserID() + "'");
   
   if(!ResultList.isEmpty()) {
        _MyUser.setFirstName(ResultList.get(0));
        _MyUser.setMiddleName(ResultList.get(1));
        _MyUser.setLastName(ResultList.get(2));
        _MyUser.setDateEntered(ResultList.get(3));
        _MyUser.setUserName(ResultList.get(4));
        _MyUser.setPassword(ResultList.get(5));
        _MyUser.setActive(Integer.parseInt(ResultList.get(6)));
        _MyUser.setAccessType(Integer.parseInt(ResultList.get(7)));
        _MyUser.setContactInformation(ResultList.get(8));
        _MyUser.setDepartmentID(ResultList.get(9));
   }else{
        _MyUser.reset();
   }
    
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
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
