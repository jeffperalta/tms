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
import java.util.ArrayList;
import utilities.generalSqlHelper;

/**
 *
 * @author jperalta
 */
public class ProjectSearchResult extends HttpServlet {
   
    private Project _MyProject = null;
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
    _MyProject = (Project)request.getSession().getAttribute("BProject");
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/
    _MyProject.setProjectID(request.getParameter("PI").trim());
    if(_MyProject.getProjectID().trim().length() != 0) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("project_name");
        myFieldList.add("start_date");
        myFieldList.add("end_date");
        myFieldList.add("client_id");
        myFieldList.add("project_amount");
        myFieldList.add("remarks");
        
        myResultList = _MyGeneralSqlHelper.getDataElement("a_project", myFieldList, "project_id='" + _MyProject.getProjectID() + "'");
        
        if(myResultList.size()!=0) {
            _MyProject.setProjectName(myResultList.get(0));
            _MyProject.setStartDate(myResultList.get(1));
            _MyProject.setEndDate(myResultList.get(2));
            _MyProject.setClientID(myResultList.get(3));
            _MyProject.setProjectAmount(Double.parseDouble(myResultList.get(4)));
            _MyProject.setRemarks(myResultList.get(5));
        }else{
            _MyProject.reset();
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
