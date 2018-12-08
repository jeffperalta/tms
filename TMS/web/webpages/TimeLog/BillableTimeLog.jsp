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
<jsp:useBean  id="myActivityList" class="beans.ActivityList" scope="session"/>
<jsp:useBean  id="myBillableTimeLogPage" class="beans.pages.BillableTimeLogPage" scope="session"/>
<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>
<jsp:useBean  id="PWorkUtilityPage" class="beans.pages.WorkUtilityPage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
	beans.ActivityList _MyActivityList = (beans.ActivityList)session.getAttribute("myActivityList");
    beans.pages.BillableTimeLogPage _MyPage =(beans.pages.BillableTimeLogPage)session.getAttribute("myBillableTimeLogPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
	utilities.Combo myCombo = new utilities.Combo();
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
			<h1 class="title">Activity Schedule<a name="main" id="main"></a> </h1>
			<div class="content"><img src="../../images/Piso.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
				<ul>
					<li><strong>Project Name: <%=_MyActivityList.getProjectName()%><span class="title"><a name="Save" id="Save"></a></span></strong></li>
					<li>Client Name: <%=_MyActivityList.getClientName()%></li>
					<li>
						<%
							String strActivityListStatusName = " ";
							if(_MyActivityList.getActivityListStatus() == 1) {
								strActivityListStatusName = "<span class='RedIndicator'>Finished</span>";
							}else{
								strActivityListStatusName = "On Going";
							}
						%>
						Project Status: <%=strActivityListStatusName%>
					</li>
					<li>Date Started: <%=_MyActivityList.getDateAssigned()%></li>
					<li>Deadline: <%=_MyActivityList.getDeadLine()%></li>
					<li>Man Hours: <%=utilities.Formatter.toDouble(_MyActivityList.getHoursToWork())%></li>
					<li>Remarks: <%=_MyActivityList.getActivityRemarks()%></li>
				</ul>
		  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/sBillableTimeLogPage">
		<div id="example" class="post">
			<h2 class="title"> New Billable Time Log </h2>
			<div class="content">
			  <fieldset>
				<ul>
					<%
						String strAllowedDays = " ";
						String strDeadline = " ";
						if(_MyActivityList.getAllowPastDate() == 0) {
							strAllowedDays = "<span class='RedIndicator'>*</span>Valid date is from <em>" + PWorkUtilityPage.getStartDateForDeadline() + "</em> to <em>" + utilities.DateUtility.now() + "</em> ";
						}else{
							strAllowedDays = "<span class='RedIndicator'>*</span><span class='CommentStyle'>Past transaction dates are now valid. Inform the administrator if all the transactions are done.</span>";
						}
						
						if(utilities.DateUtility.DateCompare(PWorkUtilityPage.getDeadline(), utilities.DateUtility.now(), constants.DateFormat.DATE_FORMAT) ==0) {
							if(_MyActivityList.getAllowPastDate() == 0) {
								strDeadline = "<span class='RedIndicator'>Deadline for this week is today. </span>Please finish all transactions since " + PWorkUtilityPage.getStartDateForDeadline() + ".";
							} else{
								strDeadline = "<span class='RedIndicator'>Deadline for this week is today. </span>";
							}
						}else{
							strDeadline = "Deadline is on " + PWorkUtilityPage.getDeadline();
						}
					%>
					<li><%=strDeadline%></li>
					<li><%=strAllowedDays%></li>
					<li>&nbsp;</li>
					<li>
						<label for="txtTransactionDate"><span class="RedIndicator">*</span>Date: </label> 
						<input name="txtTransactionDate" type="text" id="txtTransactionDate" value="<%=_MyPage.getTransactionDate()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<%
							myCombo.reset();
							myCombo.setQueryString("SELECT act_id, MID(act_name,1,45) " +
											   "FROM a_def_activity " + 
											   "WHERE dept_id='" + myUser.getDepartment() + "' AND status=1 " + 
											   "ORDER BY 2");
					  	 %>
						<label for="cdbActivityType"><span class="RedIndicator">*</span>Activity: </label> 
						<select name="cdbActivityType" id="cdbActivityType">
						<option value=" "> </option>
					    <%=myCombo.getResult()%>
						</select>
					</li>
					<li>
						<label for="txtHoursWorked"><span class="RedIndicator">*</span>Hours Worked: </label>
						<input name="txtHoursWorked" type="text" id="txtHoursWorked" value="<%=_MyPage.getRHoursWorked()%>" size="14" maxlength="6" />
					</li>
					<li>
						<label for="txtRemarks">Remarks: </label> 
						<textarea name="txtRemarks" cols="45" rows="2" id="txtRemarks"><%=_MyPage.getRemarks()%></textarea>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.ACTIVITY_LOG_ADD)%>/> <input id="glbutton" type="submit" name="glbutton" value="Back" /></li>
					<li><em><span class="CommentStyle">Check  entries using the <a href="#Filter">Time Log Search</a>.</span></em></li>
				</ul>
			  </fieldset>
			</div>
		</div>
		
		<div id="example" class="post">
			<h2 class="title"> Time Log Search <a name="Filter" id="Filter"></a><a name="Delete" id="Delete"></a></h2>
			<em><span class="CommentStyle">List my billable time logs within these dates.</span></em>
			<div class="content">
				<fieldset>
				<ul>
					<li>
						<label for="txtFromDate">Date From: </label> 
						<input name="txtFromDate" type="text" id="txtFromDate" value="<%=_MyPage.getFromDate()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtToDate">Date To: </label> 
						<input name="txtToDate" type="text" id="txtToDate" value="<%=_MyPage.getToDate()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Filter" /></li>
				</ul>
				</fieldset>
			</div>
		</div>
		<div id="table_listing">
			<div class="content">
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr bgcolor="#3B3B3B">
					  <td width="16" class="RowHeader">&nbsp;</td>
					  <td width="84" class="RowHeader">Date</td>
					  <td width="157"><div class="RowHeader">Activity</div></td>
					  <td width="163"><div class="RowHeader">Remarks</div></td>
					  <td width="76"><div align="right" class="RowHeader">Hours</div></td>
					</tr>
					<%
						tmsclass.ProjectListing.queryAnalyzer QA = new tmsclass.ProjectListing.queryAnalyzer();
					%>
					<%=QA.showActivitySchedule(_MyActivityList, _MyPage)%>
				</table>
				<fieldset>
				<ul>
					<li><input id="glbutton" type="submit" name="glbutton" value="Delete" <%=myUser.checkAccess(constants.Actions.ACTIVITY_LOG_DELETE)%>/></li>
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
				<li class="active"><a href="ProjectSelection.jsp" title="">Projects</a></li>
				<li><a href="NonBillableTimeLog.jsp" title="">Leisure</a></li>
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