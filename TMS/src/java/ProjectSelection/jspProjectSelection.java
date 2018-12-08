/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ProjectSelection;

import beans.Client;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Abree
 */

import beans.pages.ProjectSelectionPage;
import utilities.*;
import java.util.ArrayList;
import beans.*;

public class jspProjectSelection extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private ProjectSelectionPage _ThisPage = null;
    private generalSqlHelper _MySqlHelper = new generalSqlHelper();
    private Project _MyProject = null;
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
        if(_ButtonClicked.compareToIgnoreCase("projectid") == 0) {
            if(_MyProject.getProjectID().trim().length() == 0) {
                _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ProjectSearchNav");
                _MyPageNavigation.setFileName("/TMS/webpages/TimeLog/ProjectSelection.jsp#" + _ButtonClicked);
                response.sendRedirect("/TMS/webpages/SearchPages/ProjectSearch.jsp#main");
            }else{
                response.sendRedirect("/TMS/webpages/TimeLog/ProjectSelection.jsp#" + _ButtonClicked);
            }
        }
        
        if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
            response.sendRedirect("/TMS/webpages/TimeLog/ProjectSelection.jsp#" + _ButtonClicked);
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
    _ThisPage = (ProjectSelectionPage)request.getSession().getAttribute("myProjectSelectionPage");
    _MyProject = (Project)request.getSession().getAttribute("BProject");
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

    _ThisPage.setProjectStatusId(request.getParameter("cmbProjectStatus").trim());
    if(_ThisPage.getProjectStatusId().trim().length() > 0) {
        myFieldList.clear();
        myResultList.clear();

        myFieldList.add("status_name");
        myResultList = _MySqlHelper.getDataElement("a_activity_status", myFieldList, "status_id=" + _ThisPage.getProjectStatusId());

        if(myResultList.size() != 0) {
            _ThisPage.setProjectStatus(myResultList.get(0));
        }else{
            _ThisPage.setProjectStatus(" ");
            _ThisPage.setProjectStatusId(" ");
        }
    }else{
        _ThisPage.setProjectStatus(" ");
        _ThisPage.setProjectStatusId(" ");
    }

    _ThisPage.setProjectID(" ");
    if(_ThisPage.getProjectID().trim().length() > 0) {
        myFieldList.clear();
        myResultList.clear();

        myFieldList.add("project_name");
        myResultList = _MySqlHelper.getDataElement("a_project", myFieldList, "project_id='" + _ThisPage.getProjectID() + "'");

        if(myResultList.size() != 0) {
            _ThisPage.setProjectName(myResultList.get(0));
            _MyProject.setProjectID(_ThisPage.getProjectID());
            _MyProject.setProjectName(_ThisPage.getProjectName());
        }else{
            _MyProject.reset();
        }
    }else{
        _MyProject.reset();
    }

    _ThisPage.setOrderById(request.getParameter("cmbOrderBy").trim());
    if(_ThisPage.getOrderById().compareTo("a_activity_list.deadline") == 0){
        _ThisPage.setOrderBy("Deadline");
    }else if(_ThisPage.getOrderById().compareTo("a_activity.activity_name") == 0) {
        _ThisPage.setOrderBy("Project Name");
    }else if(_ThisPage.getOrderById().compareTo("a_activity_list.hrs_to_work-a_activity_list.hrs_worked") == 0) {
        _ThisPage.setOrderBy("Remaining Hours");
    }else{
        _ThisPage.setOrderBy(" ");
    }
        
/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    //No Items to Check --
/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/               
    //Variable set --
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
