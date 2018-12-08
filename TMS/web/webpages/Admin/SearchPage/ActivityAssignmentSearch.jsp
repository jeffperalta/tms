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
<jsp:useBean  id="ActivityAssignmentSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BActivityAssignment" class="beans.ActivityAssignment" scope="session"/>
<jsp:useBean  id="myActivityAssignmentSearchPage" class="beans.pages.ActivityAssignmentSearchPage" scope="session"/>

<jsp:useBean  id="BActivitySearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BActivity" class="beans.Activity" scope="session"/>

<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<jsp:useBean  id="BUserPageNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BUser" class="beans.User" scope="session"/>

<jsp:useBean  id="MessageManagerAdmin" class="message.ErrorMessage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
    beans.pages.ActivityAssignmentSearchPage _MyPage =(beans.pages.ActivityAssignmentSearchPage)session.getAttribute("myActivityAssignmentSearchPage");
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
			<h1 class="title">Project Assignment  Search<a name="main" id="main"></a> </h1>
			<div class="content"><img src="../../../images/img5.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/sActivityAssignmentSearch">
		<div id="example" class="post">
			<h2 class="title"> Criteria<a name="projectid" id="projectid"></a><a name="userid" id="userid"></a><a name="activityid" id="activityid"></a><a name="Filter" id="Filter"></a> </h2>
			<div class="content">
				<fieldset>
				<ul>
					<li>
					  <label for="txtProjectID">Project ID: </label>
                      <input name="txtProjectID" type="text" id="txtProjectID" value="<%=_MyPage.getProjectID()%>" size="22" maxlength="20" />                      
                      <input name="glbutton" type="image" value="projectid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
					  <span class="Caption"><%=_MyPage.getProjectName()%></span>
					</li>
					<li>
					  <label for="txtEmployeeID">Employee ID: </label>
					  <input name="txtEmployeeID" type="text" id="txtEmployeeID" value="<%=_MyPage.getEmployeeID()%>" size="12" maxlength="10">
                      <input name="glbutton" type="image" value="userid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
					  <span class="Caption"><%=_MyPage.getEmployeeName()%></span>
					</li>
					<li>
						 <%
						 	String chkAllowPastDate = "checked='checked'";
							if(_MyPage.getAllowedPastDate().trim().compareToIgnoreCase("1") == 0) {
								chkAllowPastDate = " ";
							}
						 %>
						 <label for="txtActivityID">Locked: </label>
						 <input name="checkbox" type="checkbox" value="checkbox"  <%=chkAllowPastDate%>/>
						 <span class="CommentStyle"><em>Show projects that were locked/unlocked.</em></span>
					</li>
					<li>
						<span class="CommentStyle"><span class="RedIndicator">NOTE:</span><em> Ensure that all the project assignments are locked. Uncheck this option to view all unlocked records.</em></span>
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
					  <td width="136" class="RowHeader">Employee Name </td>
					   <td width="262" class="RowHeader">Project Name </td>
					  <td width="98"><div class="RowHeader">Activity Status </div></td>
					</tr>
					<%
						tmsclass.ActivityAssignment.queryAnalyzer QA = new tmsclass.ActivityAssignment.queryAnalyzer();
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