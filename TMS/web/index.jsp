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
<jsp:useBean  id="LogUserInfoAdmin" class="beans.LogInfo" scope="session"/>
<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>
<jsp:useBean  id="BMessageUpdate" class="beans.pages.WorkUtilityPage" scope="session"/>
<jsp:useBean  id="MessageUpdateSearchNav" class="beans.PageNavigation" scope="session"/>

<%
	 beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
	 
	 if(myUser.isAuthenticated()) {
		response.sendRedirect("/TMS/webpages/TimeLog/ProjectSelection.jsp");
	 }else{
	 	myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
		if(myUser.isAuthenticated()) {
			response.sendRedirect("/TMS/webpages/Admin/AdminSelection.jsp");
		}
	 }
         
         myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Time Management Software by Citrus Solutions</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="header">
	 
</div>
<div id="page">
	<div id="content">
		<div id="welcome" class="post">
			<h1 class="title">Welcome to TMS!<a name="main" id="main"></a></h1>
			<div class="content"><img src="images/img4.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		<div id="example" class="post">
			<h2 class="title">Powered by Citrus Solutions </h2>
			<div class="content">
			  <blockquote>
					<p>&ldquo;Nature at its beauty is captured by time and seen by the visible eye. The essence of capturing its untamed beauty is to enlighten the way to its hidden treasures. &rdquo;</p>
			  </blockquote>
				<h3>Wisdom quotes to inspire and challenge : </h3>
				<ol>
					<li>Monks advice that for you to find inner peace balance and focus you must find the pathway to natures heart. </li>
					<li>The best remedy for those who are afraid, lonely or unhappy is to go outside, somewhere where they can be quiet, alone with the heavens, nature and God. Because only then does one feel that all is as it should be and that God wishes to see people happy, amidst the simple beauty of nature.</li>
					<li>Nature is an infinite sphere of which the center is everywhere and the circumference nowhere. </li>
				</ol>
			</div>
		</div>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li class="active"><a href="#" title="">Homepage</a></li>
				<li><a href="AboutUs.jsp" title="">About Us</a></li>
			</ul>
		</div>
		<div id="login" class="boxed">
			<h2 class="title">Client Account</h2>
			<div class="content">
				<form id="form1" method="post" action="loginpage">
					<fieldset>
					<legend>Sign-In</legend>
					<label for="txtUserName">Client ID:</label>
					<input id="txtUserName" type="text" name="txtUserName" value="" />
					<label for="txtPassword">Password:</label>
					<input id="txtPassword" type="password" name="txtPassword" value="" />
					<input id="btnSubmit" type="submit" name="btnSubmit" value="Sign In" />
                                        <p><span class="RedIndicator"><%=myUser.getDisplay()%></span></p>
					<p><a href="changepassword.jsp">Change Password</a></p>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
	<div style="clear: both;">&nbsp;</div>
</div>
<div id="footer">
	<p id="legal">Copyright &copy; 2007 Essence. All Rights Reserved. Designed by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
	<p id="links"><a href="webpages/Admin/AdminMain.jsp">Control Panel</a> | <a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a></p>
</div>
</body>
</html>
