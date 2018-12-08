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
<jsp:useBean  id="LogUserInfoAdmin" class="beans.LogInfo" scope="session"/>
<jsp:useBean  id="MessageManagerAdmin" class="message.ErrorMessage" scope="session"/>
<jsp:useBean  id="myActivityAssignmentPage" class="beans.pages.ActivityAssignmentPage" scope="session"/>

<jsp:useBean  id="ActivityAssignmentSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BActivityAssignment" class="beans.ActivityAssignment" scope="session"/>

<jsp:useBean  id="myActivitySearchPage" class="beans.pages.ActivitySearchPage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
    beans.pages.ActivityAssignmentPage _MyPage =(beans.pages.ActivityAssignmentPage)session.getAttribute("myActivityAssignmentPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManagerAdmin");
    utilities.Combo _MyCombo = new utilities.Combo();
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
<link href="../../../css/default.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div id="header">
	 
</div>
<div id="page">
	<div id="content">
		<div id="welcome" class="post">
			<h1 class="title">Project Assignment<a name="main" id="main"></a>  Information</h1>
			<div class="content"><img src="../../../images/img6.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
	  	  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/sActivityAssignmentEdit">
		<div id="example" class="post">
			<h2 class="title"> Edit Project Assignment<a name="activityassignmentid" id="activityassignmentid"></a><a name="Save" id="Save"></a><a name="Delete" id="Delete"></a></h2>
			<h3>[<a href="AdminActivityAssignmentPlus.jsp#main">Go to Add New Project Assignment</a>]</h3> 
			<div class="content">
			  <fieldset>
				<ul>
					<li>
						<%
							String strPrompt = " ";
							if(_MyPage.getProjectName().trim().length() == 0 && _MyPage.getEmployeeName().trim().length() == 0) {
								strPrompt = "<span class='CommentStyle'><em>Search the project assignment that you want to edit.</em></span>";
							}
						%>
						
						<label for="activityassignmentid">Search Project Assignment: </label> 
						<input name="glbutton" type="image" value="activityassignmentid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
						<%=strPrompt%>
					</li>
					<li>
				        <label for="txtClientID">Project Name: </label> 
					    <strong><%=_MyPage.getProjectName()%></strong>
					</li>
					<li>
						<label for="txtClientID">Employee Name: </label> 
					  <strong><%=_MyPage.getEmployeeName()%></strong>
						
			        </li>
					<li>
						<label for="txtHourToWork"><span class="RedIndicator">*</span>Hours To Work: </label>
						<input name="txtHourToWork" type="text" id="txtHourToWork" value="<%=_MyPage.getHoursToWork()%>" size="10" maxlength="6" />
					</li>
					<li>
						<label for="txtDeadline"><span class="RedIndicator">*</span>Deadline: </label>
						<input name="txtDeadline" type="text" id="txtDeadline" value="<%=_MyPage.getDeadline()%>" size="14" maxlength="12" />
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
				    <li>
						<label for="txtDateAssigned">Date Assigned: </label>
				      	<input name="txtDateAssigned" type="text" id="txtDateAssigned" value="<%=_MyPage.getDateAssigned()%>" size="14" maxlength="12" />
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
				    </li>
				    <li>
						<%
							String strAllowPastDate="";
							if(_MyPage.getAllowPastDate().trim().compareToIgnoreCase("0") == 0) {
								strAllowPastDate = "checked";
							}
						%>
						<label for="checkbox">Locked: </label>
				      	<input type="checkbox" name="checkbox" value="checkbox"  <%=strAllowPastDate%>/>
				    </li>
					<p><span class="CommentStyle"><em>Lock/Unlock past date transactions and deletion of billable activities.</em></span></p>
				    <li>
						<input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.ACTIVITY_ASSIGNMENT_EDIT)%>/> 
					  	<input id="glbutton" type="submit" name="glbutton" value="Delete" <%=myUser.checkAccess(constants.Actions.ACTIVITY_ASSIGNMENT_DELETE)%>/>
					</li>
				</ul>
			  </fieldset>
			  
			  <p align="right"><a href="../ReportPages/ActivityStatusReport.jsp">View Project Assignment Report </a></p>
			</div>
		</div>
		</form>
	</div>
	<div id="sidebar">
		
		<div id="menu">
			<ul>
				<li><a href="../../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../../AboutUs.jsp" title="">About Us</a></li>
			    <li><a href="../User/AdminUser.jsp" title="">User</a></li>
			    <li><a href="../Client/AdminClient.jsp" title=""> Client</a></li>
				
				<li><a href="../Project/AdminProject.jsp" title=""> Project</a></li>
				<li class="active"><a href="../ActivityAssignment/AdminActivityAssignment.jsp" title=""> Project Assignment</a></li>
				<li><a href="../Activity/AdminDefaultActivity.jsp" title=""> Activity</a></li>
				<li><a href="../Leaves/AdminLeave.jsp" title=""> Work Leaves</a></li>
				<li><a href="../NonBillable/NonBillable.jsp" title=""> Non-Billables</a></li>
				<li><a href="/TMS/sEnterUtility" title=""> Utilities</a></li>
				<li><a href="/TMS/webpages/Admin/ReportPages/ReportSelection.jsp" title=""> Reports</a></li>
			</ul>
		</div>
		<div id="login" class="boxed">
			<h2 class="title">Client Account</h2>
			<div class="content">
				<form id="form1" method="post" action="/TMS/LogOut">
					<fieldset>
					<legend>Sign-In</legend>
					<ul>
						<li>Client ID: <strong><%=myUser.getUsername()%></strong></li>
						<li>Name: <%=myUser.getLastName() + ", "%> <%=myUser.getFirstName()%></li>
					</ul>
					<p><input id="btnSubmit" type="submit" name="btnSubmit" value="Log Out" /></p>
					</fieldset>
				</form>
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