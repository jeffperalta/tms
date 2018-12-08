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
<jsp:useBean  id="BActivitySearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BActivity" class="beans.Activity" scope="session"/>
<jsp:useBean  id="myActivitySearchPage" class="beans.pages.ActivitySearchPage" scope="session"/>

<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<jsp:useBean  id="MessageManagerAdmin" class="message.ErrorMessage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
    beans.pages.ActivitySearchPage _MyPage =(beans.pages.ActivitySearchPage)session.getAttribute("myActivitySearchPage");
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
			<h1 class="title">Project  Search<a name="main" id="main"></a> </h1>
			<div class="content"><img src="../../../images/img6.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/sActivitySearch">
		<div id="example" class="post">
			<h2 class="title"> Criteria<a name="projectid" id="projectid"></a><a name="Filter" id="Filter"></a> </h2>
			<div class="content">
				<fieldset>
				<ul>
					<li>
					  <label for="txtFirstName">Project ID: </label>
					  <input name="txtProjectID" type="text" id="txtProjectID" value="<%=_MyPage.getProjectID()%>" size="22" maxlength="20">
					  <span class="Caption"><%=_MyPage.getProjectName()%></span>
                      <input name="glbutton" type="image" value="projectid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
					</li>
					<li>
					  <label for="txtFirstName">Project Name: </label>
					  <input name="txtActivityName" type="text" id="txtActivityName" value="<%=_MyPage.getActivityName()%>" size="30" maxlength="50">
					</li>
					<%
						_MyCombo.setQueryString("SELECT status_id, status_name FROM a_activity_status");
					%>
					<li>
					  <label for="txtLastName">Project Status: 
					  <select name="cmbStatus">
					  	<option value="<%=_MyPage.getActivityStatusID()%>"><%=_MyPage.getActivityStatusName()%></option>
						<%= _MyCombo.getResult()%>
				      </select>
					  </label>
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
					  <td width="159" class="RowHeader">Project Name </td>
					   <td width="247" class="RowHeader">Remarks</td>
					  <td width="90"><div class="RowHeader">Status</div></td>
					</tr>
					<%
						tmsclass.Activity.queryAnalyzer QA = new tmsclass.Activity.queryAnalyzer();
					%>
					<%=QA.show(_MyPage)%>
				</table>
			</div>
		</div>
		</form>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="../../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../../AboutUs.jsp" title="">About Us</a></li>
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