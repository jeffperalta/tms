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

import beans.*;
import java.util.ArrayList;
import utilities.generalSqlHelper;

public class ClientSearchResult extends HttpServlet {
   
    private PageNavigation _MyPageNavigation = null;
    private Client _MyClient = null;
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
    _MyClient = (Client)request.getSession().getAttribute("BClient");
    _MyPageNavigation = (PageNavigation)request.getSession().getAttribute("ClientSearchNav");
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
   
    _MyClient.setClientId(request.getParameter("CI").trim());
    if(_MyClient.getClientId().trim().length() != 0) {
        myFieldList.clear();
        myResultList.clear();
        
        myFieldList.add("c_name");
        myFieldList.add("c_address");
        myFieldList.add("c_contact");
        myFieldList.add("c_fax");
        myFieldList.add("c_contact_person");
        myFieldList.add("comment");
        
        myResultList = _MyGenSqlHelper.getDataElement("a_client", myFieldList, "client_id='" + _MyClient.getClientId() + "'");
        
        if(myResultList.size() != 0) {
            _MyClient.setName(myResultList.get(0));
            _MyClient.setAddress(myResultList.get(1));
            _MyClient.setContact(myResultList.get(2));
            _MyClient.setFax(myResultList.get(3));
            _MyClient.setContactPerson(myResultList.get(4));
            _MyClient.setComment(myResultList.get(5));
        }else{
            _MyClient.clear();
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
