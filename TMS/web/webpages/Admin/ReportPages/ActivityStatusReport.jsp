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
<jsp:useBean  id="myReportData" class="Report.ReportData" scope="session"/>

<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
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
			<h1 class="title">Reports</h1>
			<div class="content"><img src="../../../images/img5.jpg" alt="" width="120" height="120" class="left" />
				<strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </div>
		</div>
		<div id="example" class="post">
			<h2 class="title"> Project Status Report </h2>
			<div class="content">
				<form id="form2" method="post" action="/TMS/sAdminActivityStatus">
					<fieldset>
					<ul>
						<span class="CommentStyle"><em>Leave all parameters as blank to show all the projects and their  status.</em></span>
						<li>
							<%
								_MyCombo.setQueryString("SELECT user_id, CONCAT(UCASE(Last_name), ', ', First_name) FROM a_users");
					   		%>
							<label for="cmbUsers">Employee : </label>
							<select name="cmbUsers">
								<option value=" " selected> </option>
								<%=_MyCombo.getResult()%>
							</select>
						</li>
						<li>
							<label for="txtProjectID">Project ID : </label>
						    <input name="txtProjectID" type="text" id="txtProjectID" value="<%=BProject.getProjectID()%>" size="22" maxlength="20" />
						    <input name="glbutton" type="image" value="projectid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
                            <span class="Caption"><%=BProject.getProjectName()%></span>
						</li>
						<li> 
							<%
								_MyCombo.setQueryString("SELECT status_id, status_name FROM a_activity_status");
					   		%>
							<label for="cmbStatus">Group Project Status : </label>
							<select name="cmbStatus">
								<option value=" " selected> </option>
								<%=_MyCombo.getResult()%>
							</select>
						</li>
						<li>
							<label for="cmbStatus">Individual Project Status : </label>
							<select name="cmbStatus1">
								<option value=" " selected> </option>
								<option value="1">Finalized</option>
								<option value="0">Unfinalized</option>
							</select>
						</li>
						
						<li><input id="glbutton" type="submit" name="glbutton" value="Submit" />
						</li>
					</ul>
					</fieldset>
				</form>
			</div>
			<h3>What is this report all about?</h3>
			<ol>
              <li>What are the projects assigned to Mr. Paolo Hernandez?</li>
              <li>What are the individual and group status of all projects of Mr. Hernandez?</li>
              <li>How many man hours were rendered by Mr. Hernandez in a particular project? In all his projects?</li>
              <li>Is there any unfinalized work for this project before I close it?</li>
              <li>What projects are still on going?</li>
              <li>What is the work status of Ms. Sanchez on this particular project?</li>
              <li>Are all the projects assigned to Ms. Sanchez finalized?</li>
              <li>I want a complete list of all project status.</li>
          </ol>
		</div>
  	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="../../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../../AboutUs.jsp" title="">About Us</a></li>
			    <li ><a href="../User/AdminUser.jsp" title="">User</a></li>
			    <li><a href="../Client/AdminClient.jsp" title=""> Client</a></li>
				<li><a href="../Project/AdminProject.jsp" title=""> Project</a></li>
				<li><a href="../ActivityAssignment/AdminActivityAssignment.jsp" title=""> Project Assignment</a></li>
				<li><a href="../Activity/AdminDefaultActivity.jsp" title=""> Activity</a></li>
				<li><a href="../Leaves/AdminLeave.jsp" title=""> Work Leaves</a></li>
				<li><a href="../NonBillable/NonBillable.jsp" title=""> Non-Billables</a></li>
				<li><a href="/TMS/sEnterUtility" title=""> Utilities</a></li>
				<li class="active"><a href="ReportSelection.jsp" title=""> Reports</a></li>
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