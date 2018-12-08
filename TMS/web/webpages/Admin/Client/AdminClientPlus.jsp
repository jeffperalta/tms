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
<jsp:useBean  id="myAddClientPage" class="beans.pages.EditClientPage" scope="session"/>

<jsp:useBean  id="ClientSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BClient" class="beans.Client" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
    beans.pages.EditClientPage _MyPage =(beans.pages.EditClientPage)session.getAttribute("myAddClientPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManagerAdmin");
	beans.Client _MyClient = (beans.Client)session.getAttribute("BClient");
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
			<h1 class="title">Client Information</h1>
			<div class="content"><img src="../../../images/img7.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
	  	  </div>
		</div>
		<form id="form2" method="post" action="/TMS/sAddClientPage">
		<div id="example" class="post">
			<h2 class="title"> Add Client Info<a name="main" id="main"></a><a name="Save" id="Save"></a> </h2>
			<h3>[<a href="AdminClient.jsp#main">Go back to Edit Client Info</a>]</h3>
			<div class="content">
			  <fieldset>
				<ul>
				  <li>
						<label for="txtClientID"><span class="RedIndicator">*</span>Client ID: </label> 
						<input name="txtClientID" type="text" id="txtClientID" value="<%=_MyPage.getClientID()%>" size="22" maxlength="20" />
		          </li>
					<li>
						<label for="txtName"><span class="RedIndicator">*</span>Name: </label>
						<input name="txtName" type="text" id="txtName" value="<%=_MyPage.getClientName()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtAddress">Address: </label>
						<input name="txtAddress" type="text" id="txtAddress" value="<%=_MyPage.getAddress()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtContact">Contact: </label>
						<input name="txtContact" type="text" id="txtContact" value="<%=_MyPage.getContactInformation()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtFax">Fax: </label>
						<input name="txtFax" type="text" id="txtFax" value="<%=_MyPage.getFax()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtContactPerson">Contact Person: </label>
						<input name="txtContactPerson" type="text" id="txtContactPerson" value="<%=_MyPage.getContactPerson()%>" size="30" maxlength="50" />
					</li>
				  	<li>
						<label for="txtRemarks">Remarks: </label> 
                        <textarea name="txtRemarks" cols="45" rows="2" id="txtRemarks"><%=_MyPage.getRemarks()%></textarea>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Save" /> 
					</li>
				</ul>
			  </fieldset>
			  <p align="right"><a href="/TMS/sListing?rt=CL">View Client Listing Report</a></p>
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
			    <li class="active"><a href="../Client/AdminClient.jsp" title=""> Client</a></li>
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