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
<jsp:useBean  id="myProjectSearchPage" class="beans.pages.ProjectSearchPage" scope="session"/>

<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<jsp:useBean  id="ClientSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BClient" class="beans.Client" scope="session"/>

<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
    beans.pages.ProjectSearchPage _MyPage =(beans.pages.ProjectSearchPage)session.getAttribute("myProjectSearchPage");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
	beans.Client myClient = (beans.Client)session.getAttribute("BClient");
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
			<h1 class="title">Project Search<a name="main" id="main"></a></h1>
			<div class="content"><img src="../../images/img11.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/ProjectSearch">
		<div id="example" class="post">
			<h2 class="title"> Criteria<a name="Filter" id="Filter"></a> <a name="clientid" id="clientid"></a></h2>
			<div class="content">
				<fieldset>
				<ul>
					<li>
					  <label for="txtClientID">Client ID : </label>
					  <input name="txtClientID" type="text" id="txtClientID" value="<%=myClient.getClientId()%>" size="22" maxlength="20" />
					  <input name="glbutton" type="image" value="clientid" id="glbutton" src="../../images/search1.gif" width="28" height="20" border="0" />
                                          <span class="Caption"><%=myClient.getName()%></span>
					</li>
					<li>
                                            <label for="txtToDate">Project Name: </label> 
                                            <input name="txtProjectName" type="text" id="txtProjectName" value="<%=_MyPage.getProjectName()%>" size="30" maxlength="50">
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
					   <td width="199" class="RowHeader">Remarks</td>
					  <td width="138"><div class="RowHeader">Client Name </div></td>
					</tr>
					<%
						tmsclass.Project.queryAnalyzer QA = new tmsclass.Project.queryAnalyzer();
					%>
					<%=QA.showProject(_MyPage)%>
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