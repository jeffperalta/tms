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
<jsp:useBean  id="LeaveTypeSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BLeaveType" class="beans.LeaveType" scope="session"/>
<jsp:useBean  id="PLeaveTypePage" class="beans.pages.LeaveTypeSearchPage" scope="session"/>
<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
	
	beans.pages.LeaveTypeSearchPage myPage = (beans.pages.LeaveTypeSearchPage)session.getAttribute("PLeaveTypePage");
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
			<h1 class="title">Leave Type  Search<a name="main" id="main"></a></h1>
			<div class="content"><img src="../../images/img9.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/LeaveTypeSearch">
		<div id="example" class="post">
			<h2 class="title"> Criteria<a name="Filter" id="Filter"></a> </h2>
			<div class="content">
				<fieldset>
				<ul>
				  	<li>
					  <label for="txtClientID">Type Name: </label>
					  <input name="txtName" type="text" id="txtName" value="<%=myPage.getName()%>" size="30" maxlength="50">
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Filter" /></li>
				</ul>
				</fieldset>
			</div>
		</div>
		<div id="table_listing">
			<div class="content">
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr bgcolor="#3B3B3B">
					  <td width="202" class="RowHeader">Leave Type Name </td>
					   <td width="294" class="RowHeader">Remarks</td>
				    </tr>
					<%
						tmsclass.Leaves.queryAnalyzer QA = new tmsclass.Leaves.queryAnalyzer();
					%>
					<%=QA.showLeaveTypes(myPage)%>
				</table>
			</div>
		</div>
		</form>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../AboutUs.jsp" title="">About Us</a></li>
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