/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package NonBillableTimeLog;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import beans.NonBillableType;
import beans.PageNavigation;
import java.util.ArrayList;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class NonBillableSearchResult extends HttpServlet {
   
    private NonBillableType _MyBean = null;
    private PageNavigation _MyPageNavigation = null;
    private generalSqlHelper _MyGenSqlHelper = new generalSqlHelper();
    
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
    _MyBean = (NonBillableType)request.getSession().getAttribute("BNonBillableType");
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("NonBillableTypeSearchNav");
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
    
    _MyBean.setNonBillableTypeID(request.getParameter("NI").trim());
    if(_MyBean.getNonBillableTypeID().trim().length() != 0) {
        FieldList.clear();
        ResultList.clear();
        
        FieldList.add("non_bill_name");
        FieldList.add("remarks");
        FieldList.add("Status");
        
        ResultList = _MyGenSqlHelper.getDataElement("a_non_billable_list", FieldList, "non_bill_id=" + _MyBean.getNonBillableTypeID());
        if(ResultList.size() != 0) {
            _MyBean.setNonBillableTypeName(ResultList.get(0));
            _MyBean.setRemarks(ResultList.get(1));
            _MyBean.setStatus(Integer.parseInt(ResultList.get(2)));
        }else{
            _MyBean.reset();
        }
    }else{
        _MyBean.reset();
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
