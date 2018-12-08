/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

import beans.PageNavigation;
import beans.pages.WorkUtilityPage;
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
public class sUpdateSearchResult extends HttpServlet {
   
    private ArrayList<String> _FieldList = new ArrayList<String>();
    private ArrayList<String> _ResultList = new ArrayList<String>();
    private generalSqlHelper _MyGeneralSqlHelper = new generalSqlHelper();
    private WorkUtilityPage _MyMessage = null;
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
    _MyMessage = (WorkUtilityPage)request.getSession().getAttribute("BMessageUpdate");
    _MyPageNavigation =(PageNavigation)request.getSession().getAttribute("MessageUpdateSearchNav");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    _FieldList.clear();
    _ResultList.clear();
    
    _FieldList.add("id");
    _FieldList.add("DateCreated");
    _FieldList.add("myTitle");
    _FieldList.add("CreatedBy");
    _FieldList.add("myBody");
    _FieldList.add("show_this");
    
    if(_MyPageNavigation.getFileName().compareToIgnoreCase("/TMS/updates.jsp") == 0) {
        _ResultList = _MyGeneralSqlHelper.getDataElement("c_updates", _FieldList, "id=" + request.getParameter("ID").trim() + " AND show_this=1");
    }else{
        _ResultList = _MyGeneralSqlHelper.getDataElement("c_updates", _FieldList, "id=" + request.getParameter("ID").trim());
    }
    
    if(!_ResultList.isEmpty()) {
        _MyMessage.setMessageNumber(_ResultList.get(0));
        _MyMessage.setDateEntered(_ResultList.get(1));
        _MyMessage.setMessageTitle(_ResultList.get(2));
        _MyMessage.setAuthor(_ResultList.get(3));
        
        if(_MyPageNavigation.getFileName().trim().contains("updates.jsp")) {
            _MyMessage.setMessageBody(_ResultList.get(4).replaceAll("\\r\\n", "<BR>")); //User viewing
        }else{
            _MyMessage.setMessageBody(_ResultList.get(4)); //Admin Viewing
        }
        
        _MyMessage.setVisible(_ResultList.get(5));
    }else{
        _MyMessage.resetMessage();
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
