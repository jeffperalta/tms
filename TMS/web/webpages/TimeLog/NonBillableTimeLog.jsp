<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Title      : Essence
Version    : 1.0
Released   : 20070518
Description: A two-column fixed-width template. Suitable for blogs and small business websites.

-->
<jsp:useBean  id="LogUserInfo" class="beans.LogInfo" scope="session"/>
<jsp:useBean  id="NonBillableTypeSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BNonBillableType" class="beans.NonBillableType" scope="session"/>
<jsp:useBean  id="PNonBillableTypePage" class="beans.pages.NonBillableTypeSearchPage" scope="session"/>

<jsp:useBean  id="PNonBillableTimeLogPage" class="beans.pages.NonBillableTimeLogPage" scope="session"/>
<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>


<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
	beans.pages.NonBillableTimeLogPage myPage = (beans.pages.NonBillableTimeLogPage)session.getAttribute("PNonBillableTimeLogPage");
	beans.NonBillableType _NonBillableType = (beans.NonBillableType)session.getAttribute("BNonBillableType");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");

%>


<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Time Management Software by Citrus Solutions</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="../../css/default.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div id="header">
	 
</div>
<div id="page">
	<div id="content">
		<div id="welcome" class="post">
			<h1 class="title">Activity Schedule<a name="main" id="main"></a></h1>
			<div class="content"><img src="../../images/img12.jpg" alt="" width="120" height="120" class="left" />
		  		<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
				<ul>
					<li><Strong>Activity Name: <%=_NonBillableType.getNonBillableTypeName()%></Strong><a name="nonbilltype" id="nonbilltype"></a><a name="Save" id="Save"></a></li>
					<li>Remarks: <%=_NonBillableType.getRemarks()%></li>
		        </ul>
		  </div>
		</div>
		<form id="form2" method="post" action="/TMS/jspNonBillableScheduleLog">
		<div id="example" class="post">
			<h2 class="title"> New Non-Billable Time Log </h2>
			<div class="content">
			  <fieldset>
				<ul>
					<li>
						<label for="txtDate"><span class="RedIndicator">*</span>Date: </label> 
						<input name="txtDate" type="text" id="txtDate" value="<%=myPage.getTransactionDate()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
				    </li>
					<li>
						<label for="txtDate"><span class="RedIndicator">*</span>Type: </label> 
						<input name="txtType" type="text" id="txtType" value="<%=_NonBillableType.getNonBillableTypeID()%>" size="10" maxlength="4">
                        <input name="glbutton" type="image" value="nonbilltype" id="glbutton" src="../../images/search1.gif" width="28" height="20" border="0">
                            <span class="Caption"><%=_NonBillableType.getNonBillableTypeName()%></span>
				    </li>
					<li>
						  
					</li>
					<li>
						<label for="txtHoursWorked"><span class="RedIndicator">*</span>Hours Elapsed: </label>
					  <input name="txtHoursElapsed" type="text" id="txtHoursElapsed" value="<%=myPage.getHoursElapsed()%>" size="10" maxlength="6" />
					</li>
					<li>
						<label for="txtRemarks">Remarks: </label> 
						<textarea name="txtRemarks" cols="45" rows="2" id="txtRemarks"><%=myPage.getRemarks()%></textarea>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.NON_BILL_LOG_ADD)%>/>
					</li>
					<li><em><span class="CommentStyle">Check  entries using the <a href="#Filter">Time Log Search</a>.</span></em></li>
				</ul>
			  </fieldset>
			</div>
		</div>
		
		<div id="example" class="post">
			<h2 class="title"> Time Log Search<a name="Filter" id="Filter"></a><a name="Delete" id="Delete"></a> </h2>
			<em><span class="CommentStyle">List my non-billable time logs within these dates.</span></em>
			<div class="content">
				<fieldset>
				<ul>
					<li>
						<label for="txtFromDate">Date From: </label> 
						<input name="txtFromDate" type="text" id="txtFromDate" value="<%=myPage.getDateFrom()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtToDate">Date To: </label> 
						<input name="txtToDate" type="text" id="txtToDate" value="<%=myPage.getDateTo()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Filter"/></li>
				</ul>
				</fieldset>
			</div>
		</div>
		<div id="table_listing">
			<div class="content">
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr bgcolor="#3B3B3B">
					  <td width="16" class="RowHeader"></td>
					  <td width="80" class="RowHeader">Date</td>
					  <td width="122"><div class="RowHeader">Type</div></td>
					  <td width="194"><div class="RowHeader">Remarks</div></td>
					  <td width="84"><div align="right" class="RowHeader">Hours</div></td>
					</tr>
					<%
                        tmsclass.nonbillables.queryAnalyzer _QA = new tmsclass.nonbillables.queryAnalyzer();
                    %>
                    <%=_QA.showNonBillableSchedule(myPage, myUser)%>
				</table>
				<fieldset>
				<ul>
					<li><input id="glbutton" type="submit" name="glbutton" value="Delete" <%=myUser.checkAccess(constants.Actions.NON_BILL_LOG_DELETE)%>/></li>
				</ul>
				<p align="right"><a href="../ReportPages/TimeLogReports.jsp">View Timelog Report</a></p>
				</fieldset>
			</div>
		</div>
		</form>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../AboutUs.jsp" title="">About Us</a></li>
				<li><a href="ProjectSelection.jsp" title="">Projects</a></li>
				<li class="active"><a href="#" title="">Leisure</a></li>
				<li><a href="../Leaves/LeaveTimeLog.jsp" title="">Work Leaves</a></li>
				<li><a href="../ReportPages/ReportSelection.jsp" title="">Reports</a></li>
			</ul>
		</div>
		<div id="login" class="boxed">
		
			<h2 class="title">Client Account</h2>
			<div class="content">
				<fieldset>
				<form id="form1" method="post" action="/TMS/LogOut">
				<legend>Sign-In</legend>
				<ul>
					<li>Client ID: <strong><%=myUser.getUsername()%></strong></li>
					<li>Name: <%=myUser.getLastName() + ", "%> <%=myUser.getFirstName()%></li>
				</ul>
				<p>
				  <input id="btnSubmit" type="submit" name="btnSubmit" value="Log Out" />
				</p>
				</form>
				</fieldset>
			</div>
		</div>
	</div>
	<div style="clear: both;">&nbsp;</div>
</div>
<div id="footer">
	<p id="legal">Copyright &copy; 2007 Essence. All Rights Reserved. Designed by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
	<p id="links"><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a></p>
</div>
</body>
</html>
<%=_MyError.getMessage()%>