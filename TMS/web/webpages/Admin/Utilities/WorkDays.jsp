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
<jsp:useBean  id="PWorkUtilityPage" class="beans.pages.WorkUtilityPage" scope="session"/>
<jsp:useBean  id="MessageManagerAdmin" class="message.ErrorMessage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
	beans.pages.WorkUtilityPage myPage = (beans.pages.WorkUtilityPage)session.getAttribute("PWorkUtilityPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManagerAdmin");
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
			<h1 class="title">Work Days Information<a name="main" id="main"></a> </h1>
			<div class="content"><img src="../../../images/img12.jpg" alt="" width="120" height="120" class="left" />
		  		<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		
		<form id="form3" method="post" action="/TMS/sEditUtilities">
		<div id="example" class="post">
			<h2 class="title"> Work Days<a name="SaveWorkDays" id="Save"></a></h2>
			<div class="content">
			  <fieldset>
				<ul>
					<%
						utilities.generalSqlHelper _Sql = new utilities.generalSqlHelper();
						java.util.ArrayList<String> _In = new java.util.ArrayList<String>();
						java.util.ArrayList<String> _Out = new java.util.ArrayList<String>();
						
						_In.clear(); _Out.clear();
						_In.add("DLofSubmission");
						_In.add("DaysAllowed");
						
						_Out = _Sql.getDataElement("a_work_days", _In, "id=1");
					%>
					<li>
						<span class="CommentStyle"><span class="RedIndicator">*</span><em>Valid project logs are from <strong><%=utilities.DateUtility.FormatDate(utilities.DateUtility.addDays(_Out.get(0), -Integer.parseInt(_Out.get(1))), constants.DateFormat.DATE_FORMAT, constants.DateFormat.DATE_DISPLAY_FORMAT)%></strong> to <strong><%=utilities.DateUtility.FormatDate(_Out.get(0), constants.DateFormat.DATE_FORMAT, constants.DateFormat.DATE_DISPLAY_FORMAT)%></strong>.</em></span>
					</li>
					<li>
						<label for="txtDeadline"><span class="RedIndicator">*</span>Deadline: </label>
						<input name="txtDeadline" type="text" id="txtDeadline" value="<%=myPage.getDeadline()%>" size="14" maxlength="12"/>
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtAllowedDays"><span class="RedIndicator">*</span>Allowed days: </label>
						<input name="txtAllowedDays" type="text" id="txtAllowedDays" value="<%=myPage.getAllowedDays()%>" size="10" maxlength="3" />
					</li>
				    <li>
						<input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.EDIT_WORK_DAYS)%>/>
				    </li>
				    <p><span class="CommentStyle"><span class="RedIndicator">NOTE: </span><em>To allow a single user to do billable transactions outside the deadline go to <a href="../ActivityAssignment/AdminActivityAssignment.jsp">Edit Project Assignment</a>. </em></span></p>
				</ul>
			  </fieldset>
			</div>
		</div>
		</form>
		
		<form id="form2" method="post" action="/TMS/sNewUpdate">
		<div id="example" class="post">
			<h2 class="title">Add New Update!<a name="SaveUpdate" id="SaveUpdate"></a></h2>
			<h3>[<a href="EditUpdate.jsp#updateid">Go to Edit Updates!</a>]</h3>
			<div class="content">
			  <fieldset>
				<ul>
					<li>
						<label for="txtPosted">Posted:</label> <%=utilities.DateUtility.now()%>
					</li>
					<li>
						<label for="txtTitle"><span class="RedIndicator">*</span>Title: </label>
						<input name="txtTitle" type="text" id="txtTitle" value="<%=myPage.getMessageTitle()%>" size="30" maxlength="100"/>
					</li>
					<li>
						<label for="txtBody"><span class="RedIndicator">*</span>Message </label>
						<textarea name="txtBody" cols="46" rows="10" id="txtBody"><%=myPage.getMessageBody()%></textarea>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Save"/></li>
				</ul>
			  </fieldset>
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
				<li><a href="../ActivityAssignment/AdminActivityAssignment.jsp" title=""> Project Assignment</a></li>
				<li><a href="../Activity/AdminDefaultActivity.jsp" title=""> Activity</a></li>
				<li><a href="../Leaves/AdminLeave.jsp" title=""> Work Leaves</a></li>
				<li><a href="../NonBillable/NonBillable.jsp" title=""> Non-Billables</a></li>
				<li class="active"><a href="/TMS/sEnterUtility" title=""> Utilities</a></li>
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