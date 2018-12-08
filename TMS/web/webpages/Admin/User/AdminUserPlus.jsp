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
<jsp:useBean  id="myUserAddPage" class="beans.pages.UserPage" scope="session"/>

<jsp:useBean  id="BUserPageNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BUser" class="beans.User" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
    beans.pages.UserPage _MyPage =(beans.pages.UserPage)session.getAttribute("myUserAddPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManagerAdmin");
	beans.User _MyUser = (beans.User)session.getAttribute("BUser");
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
			<h1 class="title">User Information</h1>
			<div class="content"><img src="../../../images/img11.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
	  	  </div>
		</div>
		<form id="form2" method="post" action="/TMS/sUserAdd">
		<div id="example" class="post">
			<h2 class="title"> Add User Info<a name="main" id="main"></a><a name="Save" id="Save"></a> </h2>
			<h3>[<a href="AdminUser.jsp">Go back to Edit Client Info</a>]</h3>
			<div class="content">
			  <fieldset>
				<ul>
					<li>
						<label for="txtDateEntered">Date Entered: </label>
						<input name="txtDateEntered" type="text" id="txtDateEntered" value="<%=_MyPage.getDateEntered()%>" size="14" maxlength="12" />
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtUserID"><span class="RedIndicator">*</span>User ID: </label> 
						<input name="txtUserID" type="text" id="txtUserID" value="<%=_MyPage.getUserID()%>" size="12" maxlength="10" />
						<span class="CommentStyle">/Employee's Company ID</span>
			        </li>
					<li>
						<label for="txtFirstName"><span class="RedIndicator">*</span>First Name: </label>
						<input name="txtFirstName" type="text" id="txtFirstName" value="<%=_MyPage.getFirstName()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtLastName"><span class="RedIndicator">*</span>Last Name: </label>
						<input name="txtLastName" type="text" id="txtLastName" value="<%=_MyPage.getLastName()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtMiddleName">Middle Name: </label>
						<input name="txtMiddleName" type="text" id="txtMiddleName" value="<%=_MyPage.getMiddleName()%>" size="30" maxlength="50" />
					</li>
					<li>
					    <label for="txtContact">Contact: </label>
						<input name="txtContact" type="text" id="txtContact" value="<%=_MyPage.getContactInformation()%>" size="30" maxlength="50" />
					</li>
					<li>
						<%
					      _MyCombo.setQueryString("SELECT dept_id, dept_name FROM a_department");
					  	%>
					  	<label for="cmbDepartmentID"><span class="RedIndicator">*</span>Department ID: </label> 
					  	<select name="cmbDepartmentID">
						  	<option value="<%=_MyPage.getDepartmentID()%>" selected><%=_MyPage.getDepartmentName()%></option>
						  	<%=_MyCombo.getResult()%>
					  	</select>
					</li>
					<li>&nbsp;</li>
				  <li>
						<label for="txtUserName"><span class="RedIndicator">*</span>Username: </label>
						<input name="txtUserName" type="text" id="txtUserName" value="<%=_MyPage.getUserName()%>" size="22" maxlength="20" />
				  	  <span class="CommentStyle">/Used during log-in </span>
				  </li>
					<li>
						<label for="txtPassword"><span class="RedIndicator">*</span>Password: </label>
						<input name="txtPassword" type="password" id="txtPassword" value="<%=_MyPage.getPassword()%>" size="22" maxlength="20" />
					</li>
					<li>
						<label for="txtConfirm"><span class="RedIndicator">*</span>Confirm: </label>
						<input name="txtConfirm" type="password" id="txtConfirm" value="<%=_MyPage.getConfirmPassword()%>" size="22" maxlength="20" />
					</li>
					<li>
					   <label for="cmbAccessType"><span class="RedIndicator">*</span>Access Type : </label>
					   <%
						_MyCombo.setQueryString("SELECT access_type_id, access_type_name FROM a_access_type");
					   %>
					   <select name="cmbAccessType"><option value="<%= _MyPage.getAccessTypeR() %>"><%= _MyPage.getAccessTypeName() %></option><%= _MyCombo.getResult()%></select>
					</li>
					<li>
						<input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.USER_CREATE)%>/> 
				  	</li>
					<li><span class="CommentStyle RedIndicator">NOTE:</span></li>
					<li><span class="CommentStyle"><span class="RedIndicator">*</span><em><strong>System Type</strong> is for the administrators that have access to ALL modules.</em></span></li>
					<li><span class="CommentStyle"><span class="RedIndicator">*</span><em><strong>Transactional Type </strong>is for the users that log daily project schedule.</em></span></li>
					<li><span class="CommentStyle"><span class="RedIndicator">*</span><em><strong>Controller Type</strong> is for the administrators whose job is to manage all projects.</em></span></li>
			      	
				</ul>
			  </fieldset>
			  <p align="right"><a href="/TMS/sListing?rt=UL">View User Listing Report</a></p>
			</div>
		</div>
		</form>
	</div>
	<div id="sidebar">
		
		<div id="menu">
			<ul>
				<li><a href="../../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../../AboutUs.jsp" title="">About Us</a></li>
			    <li class="active"><a href="../User/AdminUser.jsp" title="">User</a></li>
			    <li><a href="../Client/AdminClient.jsp" title=""> Client</a></li>
				<li><a href="../Project/AdminProject.jsp" title=""> Project</a></li>
				<li><a href="../ActivityAssignment/AdminActivityAssignment.jsp" title=""> Project Assignment</a></li>
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