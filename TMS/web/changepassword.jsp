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
<jsp:useBean  id="myChangePasswordPage" class="beans.pages.ChangePasswordPage" scope="session"/>

<%
	beans.pages.ChangePasswordPage _MyPage = (beans.pages.ChangePasswordPage)session.getAttribute("myChangePasswordPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
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
	    <form id="form2" method="post" action="/TMS/sChangePassword">
		<div id="example" class="post">
			<h2 class="title"> Change Password<a name="Save" id="Save"></a> </h2>
			<div class="content">
			  <fieldset>
				<ul>
					<li>
						<label for="txtUserName">Username: </label>
						<input name="txtUserName" type="text" id="txtUserName" value="<%=_MyPage.getUserName()%>" size="22" maxlength="20" />
					</li>
					<li>
						<label for="txtOldPassword">Password: </label>
						<input name="txtOldPassword" type="password" id="txtOldPassword" value="<%=_MyPage.getOldPassword()%>" size="22" maxlength="20" />
					</li>
					<li>
						<label for="txtNewPassword"><span class="RedIndicator">*</span>New Password : </label>
						<input name="txtNewPassword" type="password" id="txtNewPassword" value="<%=_MyPage.getNewPassword()%>" size="22" maxlength="20" />
					</li>
					<li>
						<label for="txtConfirm"><span class="RedIndicator">*</span>Confirm: </label>
						<input name="txtConfirm" type="password" id="txtConfirm" value="<%=_MyPage.getConfirmPassword()%>" size="22" maxlength="20" />
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Save" /> <input id="glbutton" type="submit" name="glbutton" value="Back" /></li>
				</ul>
			  </fieldset>
			</div>
		</div>
		</form>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="index.jsp" title="">Homepage</a></li>
				<li><a href="AboutUs.jsp" title="">About Us</a></li>
			</ul>
		</div>
		
	</div>
	<div style="clear: both;">&nbsp;</div>
</div>
<div id="footer">
	<p id="legal">Copyright &copy; 2007 Essence. All Rights Reserved. Designed by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
	<p id="links"><a href="webpages/Admin/AdminMain.jsp">Control Panel</a></a> | <a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a></p>
</div>
</body>
</html>
<%=_MyError.getMessage()%>