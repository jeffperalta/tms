/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ProjectPage;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import beans.*;
import beans.pages.ProjectSearchPage;
import java.util.ArrayList;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class ProjectSearch extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private Client _MyClient = null;
    private PageNavigation _MyPageNavigation = null;
    private ProjectSearchPage _MyPage = null;
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

    if(_ButtonClicked.compareToIgnoreCase("Filter") ==0) {
        response.sendRedirect("/TMS/webpages/SearchPages/ProjectSearch.jsp#" + _ButtonClicked);
    }
    
    if(_ButtonClicked.compareToIgnoreCase("clientid") == 0) {
        if(_MyClient.getClientId().trim().length() == 0) {
            _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ClientSearchNav");
            _MyPageNavigation.setFileName("/TMS/webpages/SearchPages/ProjectSearch.jsp#" + _ButtonClicked);
            response.sendRedirect("/TMS/webpages/SearchPages/ClientSearch.jsp#main");
        }else{
            response.sendRedirect("/TMS/webpages/SearchPages/ProjectSearch.jsp#" + _ButtonClicked);
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
    _ButtonClicked = request.getParameter("glbutton");
    _MyClient = (Client)request.getSession().getAttribute("BClient");
    _MyPage = (ProjectSearchPage)request.getSession().getAttribute("myProjectSearchPage");
    
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    
    _MyPage.setClientID(request.getParameter("txtClientID").trim());
    if(_MyPage.getClientID().trim().length() > 0) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("c_name");
        myResultList = _MyGeneralSqlHelper.getDataElement("a_client", myFieldList, "client_id='" + _MyPage.getClientID().trim() + "'");
        if(myResultList.size()  > 0) {
            _MyClient.setClientId(_MyPage.getClientID());
            _MyClient.setName(myResultList.get(0));
        }else{
            _MyClient.clear();
        }
    }
    
    _MyPage.setProjectName(request.getParameter("txtProjectName").trim());
    
    
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    //No Checking
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/

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
