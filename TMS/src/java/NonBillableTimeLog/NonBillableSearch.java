/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package NonBillableTimeLog;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import beans.pages.NonBillableTypeSearchPage;

/**
 *
 * @author jperalta
 */
public class NonBillableSearch extends HttpServlet {
   
    private String _ButtonClicked = " ";
    private NonBillableTypeSearchPage _MyPage = null;
    
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
        response.sendRedirect("/TMS/webpages/SearchPages/NonBillableTypeSearch.jsp#" + _ButtonClicked);

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
    _MyPage = (NonBillableTypeSearchPage)request.getSession().getAttribute("PNonBillableTypePage");
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
    
    if(_ButtonClicked.compareToIgnoreCase("filter") == 0) {
        _MyPage.setName(request.getParameter("txtName").trim());
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
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
