/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ActivityPage;

import beans.Activity;
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
public class sActivitySearchResult extends HttpServlet {
    private Activity _MyActivity = null;
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
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
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("BActivitySearchNav");
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
    _MyActivity =(Activity)request.getSession().getAttribute("BActivity");
    ArrayList<String> FieldList = new ArrayList<String>();
    ArrayList<String> ResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    _MyActivity.setActivityID(request.getParameter("AI").trim());
    if(_MyActivity.getActivityID().trim().length()!=0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("activity_name");
        FieldList.add("a_type");
        FieldList.add("status");
        FieldList.add("remarks");
        FieldList.add("project_id");
        
        ResultList = _MyGeneralSqlHelper.getDataElement("a_activity", FieldList, "activity_id='" + Formatter.toProperSqlWhereClause(_MyActivity.getActivityID()) + "'");
        if(!ResultList.isEmpty()) {
            _MyActivity.setActivityName(ResultList.get(0));
            _MyActivity.setActivityType(ResultList.get(1));
            _MyActivity.setActivityStatus(Integer.parseInt(ResultList.get(2)));
            _MyActivity.setRemarks(ResultList.get(3));
            _MyActivity.setProjectID(ResultList.get(4));
        }else{
            _MyActivity.reset();
        }
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
