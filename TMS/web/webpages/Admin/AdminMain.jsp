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
<jsp:useBean  id="NonBillableTypeSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BNonBillableType" class="beans.NonBillableType" scope="session"/>
<jsp:useBean  id="BMessageUpdate" class="beans.pages.WorkUtilityPage" scope="session"/>

<jsp:useBean  id="PNonBillableTimeLogPage" class="beans.pages.NonBillableTimeLogPage" scope="session"/>
<jsp:useBean  id="MessageManagerAdmin" class="message.ErrorMessage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
	beans.pages.NonBillableTimeLogPage myPage = (beans.pages.NonBillableTimeLogPage)session.getAttribute("PNonBillableTimeLogPage");
	beans.NonBillableType _NonBillableType = (beans.NonBillableType)session.getAttribute("BNonBillableType");
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
<link href="../../css/default.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div id="header">
	 
</div>
<div id="page">
	<div id="content">
		<div id="welcome" class="post">
			<h1 class="title">Control Panel </h1>
			<div class="content"><img src="../../images/img14.jpg" alt="" width="120" height="120" class="left" />
		  		<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
		</div>
		<div id="example" class="post">
			<h2 class="title">Powered by Citrus Solutions </h2>
			<div class="content">
				<p> This module is accessible only to managers or other system type users. Allowing each user to change and customize the functionality of the TMS and create new user accounts.</p>
				<blockquote>
					<p>&ldquo;<font face="georgia, bookman old style, palatino linotype, book antiqua, palatino, trebuchet ms, helvetica, garamond, sans-serif, arial, verdana, avante garde, century gothic, comic sans ms, times, times new roman, serif">If the sight of the blue skies fills you with joy, if a blade of grass springing up in the fields has power to move you, if the simple things of nature have a message that you understand, rejoice, for your soul is alive. </font>&rdquo; - <em><font face="georgia, bookman old style, palatino linotype, book antiqua, palatino, trebuchet ms, helvetica, garamond, sans-serif, arial, verdana, avante garde, century gothic, comic sans ms, times, times new roman, serif">Eleonora Duse</font></em></p>
			  </blockquote>
				<h3>&nbsp;</h3>
		  </div>
		</div>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../AboutUs.jsp" title="">About Us</a></li>
			</ul>
		</div>
		<div id="login" class="boxed">
			<h2 class="title">Admin Client Account</h2>
			<div class="content">
				<form id="form1" method="post" action="/TMS/sLogInPageAdmin">
					<fieldset>
					<legend>Sign-In</legend>
					<label for="txtUserName">User ID:</label>
					<input id="txtUserName" type="text" name="txtUserName" value= "" />
					<label for="txtPassword">Password:</label>
					<input id="txtPassword" type="password" name="txtPassword" value="" />
					<input id="btnSubmit" type="submit" name="btnSubmit" value="Sign In" />
                                        <p><span class="RedIndicator"><%=myUser.getDisplay()%></span></p>
					<p><a href="../../changepassword.jsp">Change Password </a></p>
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