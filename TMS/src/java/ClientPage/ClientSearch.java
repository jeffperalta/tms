/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package ClientPage;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Abree
 */

import beans.pages.ClientSearchPage;

public class ClientSearch extends HttpServlet {
   
    private ClientSearchPage _MyPage = null;
    
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
        response.sendRedirect("/TMS/webpages/SearchPages/ClientSearch.jsp#Filter");
        
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
        _MyPage = (ClientSearchPage)request.getSession().getAttribute("myClientSearchPage");
        
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/
        
        _MyPage.setName(request.getParameter("txtClientName").trim());
        
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
