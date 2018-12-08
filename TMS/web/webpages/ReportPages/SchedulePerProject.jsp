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
<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>
<jsp:useBean  id="myReportData" class="Report.ReportData" scope="session"/>

<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
	beans.Project myProject = (beans.Project)session.getAttribute("BProject");
	Report.ReportData _MyReportData = (Report.ReportData)session.getAttribute("myReportData");
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
			<h1 class="title">Reports</h1>
			<div class="content"><img src="../../images/img5.jpg" alt="" width="120" height="120" class="left" />
				<strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </div>
		</div>
		<div id="example" class="post">
			<h2 class="title"> Time Log Per Project Report </h2>
			<h3 class="title">[<a href="TimeLogReports.jsp">Go back to General Timelog Reports</a>]</h3>
			<div class="content">
				<form id="form2" method="post" action="/TMS/sSchedulePerProjectReport">
					<fieldset>
					<ul>
						<li>
						  <label for="txtProjectID"><span class="RedIndicator">*</span>Project ID: </label>
						  <input name="txtProjectID" type="text" id="txtProjectID" value="<%=myProject.getProjectID()%>" size="22" maxlength="20" />
                          <input name="glbutton" type="image" value="projectid" id="glbutton" src="../../images/search1.gif" width="28" height="20" border="0" />
						  <span class="Caption"><%=myProject.getProjectName()%></span>
						</li>
						<li>
						  <label for="txtStartDate"><span class="RedIndicator">*</span>Start Date: </label>
						  <input name="txtStartDate" type="text" id="txtStartDate" value="<%=_MyReportData.getDate1()%>" size="14" maxlength="12" />
						  <span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
						</li>
						<li>
						  <label for="txtEndDate"><span class="RedIndicator">*</span>End Date: </label>
						  <input name="txtEndDate" type="text" id="txtEndDate" value="<%=_MyReportData.getDate2()%>" size="14" maxlength="12" />
						  <span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
						</li>
						<li><input id="glbutton" type="submit" name="glbutton" value="Submit" />
						</li>
					</ul>
					</fieldset>
				</form>
			</div>
			<h3>What is this report all about?</h3>
		   	<ol>
              <li>What is my work history for this project?</li>
              <li>How many man-hours were rendered for this project on this month?</li>
          </ol>
		</div>
  	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../AboutUs.jsp" title="">About Us</a></li>
				<li><a href="../TimeLog/ProjectSelection.jsp" title="">Projects</a></li>
				<li><a href="../TimeLog/NonBillableTimeLog.jsp" title="">Leisure</a></li>
				<li><a href="../Leaves/LeaveTimeLog.jsp" title="">Work Leaves</a></li>
				<li class="active"><a href="ReportSelection.jsp" title="">Reports</a></li>
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