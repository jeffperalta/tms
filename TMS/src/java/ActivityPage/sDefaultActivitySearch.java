/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ActivityPage;

import beans.pages.DefaultActivitySearchPage;
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
public class sDefaultActivitySearch extends HttpServlet {
    private String _ButtonClicked = " ";
    private DefaultActivitySearchPage _MyPage = null;
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
    if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
        response.sendRedirect("/TMS/webpages/Admin/SearchPage/DefaultActivitySearch.jsp#" + _ButtonClicked);
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
    _MyPage = (DefaultActivitySearchPage)request.getSession().getAttribute("myDefaultActivitySearchPage");
    
    ArrayList<String> myFieldList = new ArrayList<String>();
    ArrayList<String> myResultList = new ArrayList<String>();
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    _MyPage.setActivityName(request.getParameter("txtActivityName").trim());
    _MyPage.setDepartmentID(request.getParameter("cmbDepartmentID").trim());
    
    if(_MyPage.getDepartmentID().trim().length() != 0) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("dept_name");
        myResultList = _MyGeneralSqlHelper.getDataElement("a_department", 
                myFieldList, "dept_id='" + _MyPage.getDepartmentID() + "'");
        if(!myResultList.isEmpty()) {
            _MyPage.setDepartmentName(myResultList.get(0));
        }else{
            _MyPage.setDepartmentName(" ");
        }
    }else{
        _MyPage.setDepartmentName(" ");
    }
    
    _MyPage.setStatus(request.getParameter("cmbStatus").trim());

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/
    
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
