/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ActivityPage;

import beans.DefaultActivity;
import beans.PageNavigation;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import utilities.generalSqlHelper;

/**
 *
 * @author user
 */
public class sDefaultActivitySearchResult extends HttpServlet {
    private PageNavigation _MyPageNavigation = null;
    private DefaultActivity _MyDefaultActivity = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    
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
    _MyPageNavigation = (PageNavigation) request.getSession().getAttribute("BDefaultActivitySearchNav");
    _MyDefaultActivity = (DefaultActivity) request.getSession().getAttribute("BDefaultActivity");
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyDefaultActivity.setActivityID(request.getParameter("AI").trim());
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    if(_MyDefaultActivity.getActivityID().trim().length() !=0) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("act_name");
        myFieldList.add("dept_id");
        myFieldList.add("status");
        
        myResultList = _MyGeneralSqlHelper.getDataElement("a_def_activity", 
                myFieldList, "act_id='" + _MyDefaultActivity.getActivityID() + "'");
              
        if(!myResultList.isEmpty()) {
            _MyDefaultActivity.setActivityName(myResultList.get(0));
            _MyDefaultActivity.setDepartmentID(myResultList.get(1));
            _MyDefaultActivity.setStatus(Integer.parseInt(myResultList.get(2)));
        }else{
            _MyDefaultActivity.setActivityID(" ");
            _MyDefaultActivity.setDepartmentID(" ");
            _MyDefaultActivity.setStatus(1);
            _MyDefaultActivity.setActivityID(" ");
        }
    }else{
        _MyDefaultActivity.setActivityID(" ");
        _MyDefaultActivity.setDepartmentID(" ");
        _MyDefaultActivity.setStatus(1);
        _MyDefaultActivity.setActivityID(" ");
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
