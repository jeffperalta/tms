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

<%
	 beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
	 beans.LogInfo myUser1 = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
	 
	 if(!myUser.isAuthenticated() && !myUser1.isAuthenticated()) {
		response.sendRedirect("/TMS/index.jsp");
	 }
%>

<%
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
	beans.pages.WorkUtilityPage _MyMessage = (beans.pages.WorkUtilityPage)session.getAttribute("BMessageUpdate");
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
			<h1 class="title">Updates!<a name="main" id="main"></a></h1>
			<div class="content"><img src="images/img4.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
	    <div id="example" class="post">
			<h2 class="title"><%=_MyMessage.getMessageTitle()%></h2>
			<div class="content">
				
				<%
					String strDate = " ";
					try {
						strDate = utilities.DateUtility.FormatDate(_MyMessage.getDateEntered(), constants.DateFormat.DATE_FORMAT, "MMMM dd, yyyy");
					}catch (Exception e) {
						strDate = _MyMessage.getDateEntered();
					}
				%>
				<span class="date"> Posted: <%=strDate%></span>
				<h3>Author: <strong><%=_MyMessage.getAuthor()%></strong> </h3>
				<blockquote>
					<p><em>&ldquo;<%=_MyMessage.getMessageBody()%>&rdquo;</em></p>
				</blockquote>
			</div>
		</div>
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
	<p id="links"><a href="#">Privacy Policy</a> | <a href="#">Terms of Use</a></p>
</div>
</body>
</html>
<%=_MyError.getMessage()%>