/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package LogInPage;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jperalta
 */
public class LogOut extends HttpServlet {
   
    private String _ButtonClicked = " ";
    
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
    if(_ButtonClicked.compareToIgnoreCase("Log Out") == 0) {
        response.sendRedirect("/TMS/index.jsp");
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
        _ButtonClicked = "Log Out";
        request.getSession().removeAttribute("LogUserInfo");
        request.getSession().removeAttribute("LogUserInfoAdmin");
        request.getSession().removeAttribute("MessageManager");
        request.getSession().removeAttribute("myChangePasswordPage");
        request.getSession().removeAttribute("NonBillableTypeSearchNav");
        request.getSession().removeAttribute("BNonBillableType");
        request.getSession().removeAttribute("PNonBillableTimeLogPage");
        request.getSession().removeAttribute("MessageManagerAdmin");
        request.getSession().removeAttribute("PWorkUtilityPage");
        request.getSession().removeAttribute("ProjectSearchNav");
        request.getSession().removeAttribute("BProject");
        request.getSession().removeAttribute("BActivitySearchNav");
        request.getSession().removeAttribute("BActivity");
        request.getSession().removeAttribute("myActivityPage");
        request.getSession().removeAttribute("myAddActivityPage");
        request.getSession().removeAttribute("myActivityAssignmentPage");
        request.getSession().removeAttribute("ActivityAssignmentSearchNav");
        request.getSession().removeAttribute("BActivityAssignment");
        request.getSession().removeAttribute("myActivitySearchPage");
        request.getSession().removeAttribute("myActivityAssignmentPageAdd");
        request.getSession().removeAttribute("BUserPageNav");
        request.getSession().removeAttribute("BUser");
        request.getSession().removeAttribute("myEditClientPage");
        request.getSession().removeAttribute("ClientSearchNav");
        request.getSession().removeAttribute("BClient");
        request.getSession().removeAttribute("myAddClientPage");
        request.getSession().removeAttribute("myEditLeavePage");
        request.getSession().removeAttribute("LeaveTypeSearchNav");
        request.getSession().removeAttribute("BLeaveType");
        request.getSession().removeAttribute("PLeaveTypePage");
        request.getSession().removeAttribute("myAddLeavePage");
        request.getSession().removeAttribute("myEditNonBillablePage");
        request.getSession().removeAttribute("PNonBillableTypePage");
        request.getSession().removeAttribute("myAddNonBillablePage");
        request.getSession().removeAttribute("myProjectPage");
        request.getSession().removeAttribute("myAddProjectPage");
        request.getSession().removeAttribute("myActivityAssignmentSearchPage");
        request.getSession().removeAttribute("myClientSearchPage");
        request.getSession().removeAttribute("myProjectSearchPage");
        request.getSession().removeAttribute("myUserSearchPage");
        request.getSession().removeAttribute("myUserEditPage");
        request.getSession().removeAttribute("myUserAddPage");
        request.getSession().removeAttribute("PLeaveTimeLogPage");
        request.getSession().removeAttribute("myReportData");
        request.getSession().removeAttribute("myActivityList");
        request.getSession().removeAttribute("myBillableTimeLogPage");
        request.getSession().removeAttribute("myProjectSelectionPage");
        request.getSession().removeAttribute("PEditMessagePage");
        request.getSession().removeAttribute("BMessageUpdate");
        request.getSession().removeAttribute("MessageUpdateSearchNav");
        request.getSession().removeAttribute("myDefaultActivitySearchPage");
        request.getSession().removeAttribute("BDefaultActivitySearchNav");
        request.getSession().removeAttribute("BDefaultActivity");
        request.getSession().removeAttribute("myEditDefaultActivityPage");
        request.getSession().removeAttribute("myAddDefaultActivityPage");
        request.getSession().removeAttribute("myLeaveStatusPage");
        request.getSession().removeAttribute("myNonBillableStatusPage");
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
    _ButtonClicked = request.getParameter("btnSubmit");
    
/******************************************************************************
 *--Refresh Section--                                                         *
 ******************************************************************************/

/******************************************************************************
 *--Check Input Section--                                                     *
 ******************************************************************************/

/******************************************************************************
 *--Process Section--                                                         *
 ******************************************************************************/        
    
    if(_ButtonClicked.compareToIgnoreCase("Log Out") == 0) {
        request.getSession().removeAttribute("LogUserInfo");
        request.getSession().removeAttribute("LogUserInfoAdmin");
        request.getSession().removeAttribute("MessageManager");
        request.getSession().removeAttribute("myChangePasswordPage");
        request.getSession().removeAttribute("NonBillableTypeSearchNav");
        request.getSession().removeAttribute("BNonBillableType");
        request.getSession().removeAttribute("PNonBillableTimeLogPage");
        request.getSession().removeAttribute("MessageManagerAdmin");
        request.getSession().removeAttribute("PWorkUtilityPage");
        request.getSession().removeAttribute("ProjectSearchNav");
        request.getSession().removeAttribute("BProject");
        request.getSession().removeAttribute("BActivitySearchNav");
        request.getSession().removeAttribute("BActivity");
        request.getSession().removeAttribute("myActivityPage");
        request.getSession().removeAttribute("myAddActivityPage");
        request.getSession().removeAttribute("myActivityAssignmentPage");
        request.getSession().removeAttribute("ActivityAssignmentSearchNav");
        request.getSession().removeAttribute("BActivityAssignment");
        request.getSession().removeAttribute("myActivitySearchPage");
        request.getSession().removeAttribute("myActivityAssignmentPageAdd");
        request.getSession().removeAttribute("BUserPageNav");
        request.getSession().removeAttribute("BUser");
        request.getSession().removeAttribute("myEditClientPage");
        request.getSession().removeAttribute("ClientSearchNav");
        request.getSession().removeAttribute("BClient");
        request.getSession().removeAttribute("myAddClientPage");
        request.getSession().removeAttribute("myEditLeavePage");
        request.getSession().removeAttribute("LeaveTypeSearchNav");
        request.getSession().removeAttribute("BLeaveType");
        request.getSession().removeAttribute("PLeaveTypePage");
        request.getSession().removeAttribute("myAddLeavePage");
        request.getSession().removeAttribute("myEditNonBillablePage");
        request.getSession().removeAttribute("PNonBillableTypePage");
        request.getSession().removeAttribute("myAddNonBillablePage");
        request.getSession().removeAttribute("myProjectPage");
        request.getSession().removeAttribute("myAddProjectPage");
        request.getSession().removeAttribute("myActivityAssignmentSearchPage");
        request.getSession().removeAttribute("myClientSearchPage");
        request.getSession().removeAttribute("myProjectSearchPage");
        request.getSession().removeAttribute("myUserSearchPage");
        request.getSession().removeAttribute("myUserEditPage");
        request.getSession().removeAttribute("myUserAddPage");
        request.getSession().removeAttribute("PLeaveTimeLogPage");
        request.getSession().removeAttribute("myReportData");
        request.getSession().removeAttribute("myActivityList");
        request.getSession().removeAttribute("myBillableTimeLogPage");
        request.getSession().removeAttribute("myProjectSelectionPage");
        request.getSession().removeAttribute("PEditMessagePage");
        request.getSession().removeAttribute("BMessageUpdate");
        request.getSession().removeAttribute("MessageUpdateSearchNav");
        request.getSession().removeAttribute("myDefaultActivitySearchPage");
        request.getSession().removeAttribute("BDefaultActivitySearchNav");
        request.getSession().removeAttribute("BDefaultActivity");
        request.getSession().removeAttribute("myEditDefaultActivityPage");
        request.getSession().removeAttribute("myAddDefaultActivityPage");
        request.getSession().removeAttribute("myLeaveStatusPage");
        request.getSession().removeAttribute("myNonBillableStatusPage");
    }
    
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
