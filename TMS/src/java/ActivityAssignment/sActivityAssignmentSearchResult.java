/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityAssignment;

import beans.ActivityAssignment;
import beans.PageNavigation;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;
import utilities.Formatter;
import utilities.generalSqlHelper;

/**
 *
 * @author Abree
 */
public class sActivityAssignmentSearchResult extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private PageNavigation _MyPageNavigation = null;
    private ActivityAssignment _MyActivityAssignment = null;
    private ArrayList<String> FieldList = new ArrayList<String>();
    private ArrayList<String> ResultList = new ArrayList<String>();
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
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ActivityAssignmentSearchNav");
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
    _MyActivityAssignment = (ActivityAssignment)request.getSession().getAttribute("BActivityAssignment");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    
    _MyActivityAssignment.setActivityListID(Integer.parseInt(request.getParameter("ALID").trim()));
    if(_MyActivityAssignment.getActivityListID() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("activity_id");
        FieldList.add("user_id");
        FieldList.add("hrs_to_work");
        FieldList.add("hrs_worked");
        FieldList.add("status");
        FieldList.add("deadline");
        FieldList.add("dateassigned");
        FieldList.add("allow_past_date");
        
        ResultList = _MyGeneralSqlHelper.getDataElement("a_activity_list", FieldList, "activity_list_id=" + Formatter.toProperSqlWhereClause(_MyActivityAssignment.getActivityListID() + ""));
        if(!ResultList.isEmpty()) {
            _MyActivityAssignment.setActivityID(ResultList.get(0));
            _MyActivityAssignment.setUserID(ResultList.get(1));
            _MyActivityAssignment.setHoursToWork(Double.parseDouble(ResultList.get(2)));
            _MyActivityAssignment.setHoursWorked(Double.parseDouble(ResultList.get(3)));
            _MyActivityAssignment.setStatus(Integer.parseInt(ResultList.get(4)));
            _MyActivityAssignment.setDeadline(ResultList.get(5));
            _MyActivityAssignment.setDateAssigned(ResultList.get(6));
            _MyActivityAssignment.setAllowPastDate(Integer.parseInt(ResultList.get(7)));
        }else{
            _MyActivityAssignment.reset();
        }
               
    }
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
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
