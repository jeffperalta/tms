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
<jsp:useBean  id="myLeaveStatusPage" class="beans.pages.LeaveStatusPage" scope="session"/>

<jsp:useBean  id="BUserPageNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BUser" class="beans.User" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
    beans.pages.LeaveStatusPage _MyPage =(beans.pages.LeaveStatusPage)session.getAttribute("myLeaveStatusPage");
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
			<h1 class="title">Leave Security<a name="main" id="main"></a></h1>
			<div class="content"><img src="../../../images/img8.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
	  	  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/sAdminLeaveStatus">
		<div id="example" class="post">
			<h2 class="title"> Leave Unlock/Lock <a name="userid" id="userid"></a><a name="Filter" id="Filter"></a></h2>
			<h3>[<a href="AdminLeave.jsp#main">Go back to Edit Leave Information</a>]</h3>
			<div class="content">
			  <fieldset>
				<ul>
				  	<li>
						<%
							String strPrompt = " ";
							if(_MyPage.getUserID().trim().length() == 0) {
								strPrompt = "<span class='CommentStyle'><em>Search the user to view all his leave history</em></span>";
							}
						%>
						<label for="txtName"><span class="RedIndicator">*</span>User ID: </label>
                        <%=_MyPage.getUserID()%>  <strong><%=_MyPage.getUserName()%></strong>
						<input name="glbutton" type="image" value="userid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
						<%=strPrompt%>
					</li>
					<li>
						<label for="txtStartDate">Start Date: </label>
						<input name="txtStartDate" type="text" id="txtStartDate" value="<%=_MyPage.getStartDate()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtEndDate">End Date: </label>
						<input name="txtEndDate" type="text" id="txtEndDate" value="<%=_MyPage.getEndDate()%>" size="14" maxlength="12">
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<input id="glbutton" type="submit" name="glbutton" value="Filter"/> 
					</li>
				</ul>
			  </fieldset>
			</div>
		</div>
		<p><span class="CommentStyle"><em>Lock/Unlock deletion of work leave activities.</em></span></p>
		<div id="table_listing">
			<div class="content">
				<a name="Save" id="Save"></a>
				<table width="100%" cellspacing="0" cellpadding="0">
					<tr bgcolor="#3B3B3B">
					  <td width="53" class="RowHeader">Locked</td>
					  <td width="88" class="RowHeader">Date</td>
					  <td width="143" class="RowHeader">Leave</td>
					  <td width="212" class="RowHeader">Remarks</td>
					</tr>
					<%
						tmsclass.Leaves.queryAnalyzer QA = new tmsclass.Leaves.queryAnalyzer();
					%>
					<%=QA.showLeaves(_MyPage)%>
				</table>
				<input id="glbutton" type="submit" name="glbutton" value="Save"/> 
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
			    <li><a href="../Client/AdminClient.jsp" title=""> Client</a></li>
				<li><a href="../Project/AdminProject.jsp" title=""> Project</a></li>
				<li><a href="../ActivityAssignment/AdminActivityAssignment.jsp" title=""> Project Assignment</a></li>
				<li><a href="../Activity/AdminDefaultActivity.jsp" title=""> Activity</a></li>
				<li class="active"><a href="../Leaves/AdminLeave.jsp" title=""> Work Leaves</a></li>
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