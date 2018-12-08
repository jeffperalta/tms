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
<jsp:useBean  id="myProjectPage" class="beans.pages.ProjectPage" scope="session"/>


<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<jsp:useBean  id="ClientSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BClient" class="beans.Client" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfoAdmin");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
    beans.pages.ProjectPage _MyPage =(beans.pages.ProjectPage)session.getAttribute("myProjectPage");
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
			<h1 class="title">Project Information<a name="main" id="main"></a></h1>
			<div class="content"><img src="../../../images/img10.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
	  	  </div>
	  </div>
		<form id="form2" method="post" action="/TMS/sEditProject">
		<div id="example" class="post">
			<h2 class="title"> Edit Project Info<a name="projectid" id="projectid"></a><a name="clientid" id="clientid"></a><a name="Save" id="Save"></a><a name="Save" id="Save"></a> </h2>
			<h3>[<a href="AdminProjectPlus.jsp#main">Go to Add New Project Info</a>]</h3>
			<div class="content">
			  <fieldset>
				<ul>
					<li>
						<%
							String strPrompt = " ";
							if(_MyPage.getProjectID().trim().length() == 0) {
								strPrompt = "<span class='CommentStyle'><em>Search the project that you want to edit.</em></span>";
							}
						%>
						<label for="txtProjectID">Project ID: </label> 
						<strong><%=_MyPage.getProjectID()%></strong>
						<input name="glbutton" type="image" value="projectid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
						<%=strPrompt%>
			        </li>
					<li>
					  <label for="txtClientID"><span class="RedIndicator">*</span>Client ID: </label> 
						<input name="txtClientID" type="text" id="txtClientID" value="<%=_MyPage.getClientID()%>" size="22" maxlength="20" />
						<span class="Caption"><%=_MyPage.getClientName()%></span>
						<input name="glbutton" type="image" value="clientid" id="glbutton" src="../../../images/search1.gif" width="28" height="20" border="0" />
			        </li>
					<li>
						<label for="txtName"><span class="RedIndicator">*</span>Project Name : </label>
						<input name="txtName" type="text" id="txtName" value="<%=_MyPage.getProjectName()%>" size="30" maxlength="50" />
					</li>
					<li>
						<label for="txtStartDate">Start Date : </label>
						<input name="txtStartDate" type="text" id="txtStartDate" value="<%=_MyPage.getStartDate()%>" size="14" maxlength="12" />
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtEndDate">End Date : </label>
						<input name="txtEndDate" type="text" id="txtEndDate" value="<%=_MyPage.getEndDate()%>" size="14" maxlength="12" />
						<span class="CommentStyle">[<%=constants.DateFormat.DATE_FORMAT%>]</span>
					</li>
					<li>
						<label for="txtAmount">Project Amount : </label>
						<input name="txtAmount" type="text" id="txtAmount" value="<%=_MyPage.getProjectAmount()%>" size="12" maxlength="10" />
					</li>
				  	<li>
						<label for="txtRemarks">Remarks: </label> 
                        <textarea name="txtRemarks" cols="45" rows="2" id="txtRemarks"><%=_MyPage.getRemarks()%></textarea>
					</li>
					<li><input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.PROJECT_EDIT)%>/> 
					  <input id="glbutton" type="submit" name="glbutton" value="Delete" <%=myUser.checkAccess(constants.Actions.PROJECT_DELETE)%>/>
					</li>
					<li>&nbsp;<a name="Reopen" id="ChangeStatus"></a></li>
					<li>
						<%
							String strAccess = myUser.checkAccess(constants.Actions.PROJECT_EDIT);
							String strOnGoing = "<input id='glbutton' type='submit' name='glbutton' value='Reopen' " + strAccess + "/>";
							String strClose = "<input id='glbutton' type='submit' name='glbutton' value='Close Project' " + strAccess + "/>";
							String strCancelled = "<input id='glbutton' type='submit' name='glbutton' value='Cancel Project' " + strAccess + "/>";
							String strAdditionalInfo = " ";
							String strReopen="<input id='glbutton' type='submit' name='glbutton' value='Reopen' disabled/>";
							String strAdditionalComment = "<span class='CommentStyle'><em><Strong>Cancel a project</Strong> if it was discontinued before reaching its target date.</em></span></li><li><span class='CommentStyle'><em><Strong>Closed projects</Strong> are those that were successfully finished.</em></span>";
							
							if(_MyPage.getProjectID().trim().length()==0) {
								strOnGoing = " ";
								strCancelled = " ";
								strClose = " ";
								strAdditionalInfo = " ";
								strReopen = " ";
								strAdditionalComment = " ";
							}else if(_MyPage.getProjectStatus().compareToIgnoreCase("On Going") == 0) {
								strOnGoing = " ";
								strAdditionalInfo = "<span class='CommentStyle'><em>Time logs are now active.</em></span>";
							}else if(_MyPage.getProjectStatus().compareToIgnoreCase("Cancelled") == 0) {
								strCancelled = " ";
								strClose = " ";
								strReopen = " ";
								strAdditionalInfo = "<span class='CommentStyle'><em>Time logs are now inactive.</em></span>";
							}else if(_MyPage.getProjectStatus().compareToIgnoreCase("Close") == 0) {
								strCancelled = " ";
								strClose = " ";
								strReopen = " ";
								strAdditionalInfo = "<span class='CommentStyle'><em>Regardless of individual project status, the group project is now close.</em></span>";
							}
						%>
						<label for="txtCurrentStatus">Current Status: </label> 
						<strong><%=_MyPage.getProjectStatus()%></strong>
						<p><%=strAdditionalInfo%></p>
					</li>
					<li>
						<%=strOnGoing%> <%=strClose%> <%=strCancelled%> <%=strReopen%>
					</li>
					<li>
						<%=strAdditionalComment%>
					</li>
					<p align="right"><a href="../ReportPages/ActivityStatusReport.jsp">View Project Status</a></p>
				</ul>
			  </fieldset>
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
				<li class="active"><a href="../Project/AdminProject.jsp" title=""> Project</a></li>
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