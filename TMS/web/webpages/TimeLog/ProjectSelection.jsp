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
<jsp:useBean  id="myProjectSelectionPage" class="beans.pages.ProjectSelectionPage" scope="session"/>
<jsp:useBean  id="myActivityList" class="beans.ActivityList" scope="session"/>

<jsp:useBean  id="ProjectSearchNav" class="beans.PageNavigation" scope="session"/>
<jsp:useBean  id="BProject" class="beans.Project" scope="session"/>

<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>

<jsp:useBean  id="BMessageUpdate" class="beans.pages.WorkUtilityPage" scope="session"/>
<jsp:useBean  id="MessageUpdateSearchNav" class="beans.PageNavigation" scope="session"/>

<jsp:useBean  id="PWorkUtilityPage" class="beans.pages.WorkUtilityPage" scope="session"/>

<%
	beans.LogInfo myUser = (beans.LogInfo)session.getAttribute("LogUserInfo");
        
        if(!myUser.isAuthenticated()) {
            response.sendRedirect("/TMS/index.jsp");
        }
        
	beans.pages.ProjectSelectionPage thisPage  = (beans.pages.ProjectSelectionPage)session.getAttribute("myProjectSelectionPage");
	utilities.Combo myCombo = new utilities.Combo();
	beans.Project myProject = (beans.Project)session.getAttribute("BProject");
	message.ErrorMessage _MyError = (message.ErrorMessage)session.getAttribute("MessageManager");
	
	beans.PageNavigation _MyPageNavigation = (beans.PageNavigation)session.getAttribute("MessageUpdateSearchNav");
	_MyPageNavigation.setFileName("/TMS/updates.jsp");
	
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
			<h1 class="title">Project Selection <a name="main" id="main"></a></h1>
			<div class="content"><img src="../../images/Piso.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>TMS</strong> is a Time Management Software that allows you to manage and track the activities that are within assigned projects. Work leaves and non-working activities can also be recorded and monitored using this application. Thus, reports are generated for easy access in monitoring. </p>
		  </div>
	  </div>
		<div id="example" class="post">
			<h2 class="title"> Project Search<a name="projectid" id="projectid"></a></h2>
			<div class="content">
				<form id="form2" method="post" action="/TMS/jspProjectSelection">
					<fieldset>
					<ul>
						<li>
						  <%
							myCombo.reset();
							myCombo.setQueryString("SELECT status_id, status_name " +
												   "FROM a_activity_status ");
					  	  %>
						  <label for="cmbProjectStatus"> Status: </label>
						  <select name="cmbProjectStatus">
                            <option selected="selected" value="<%=thisPage.getProjectStatusId()%>"><%=thisPage.getProjectStatus()%></option>
                            <option value=" "> </option>
                            <%=myCombo.getResult()%>
                          </select>
						</li>
					    <li>
						  <label for="cmbOrderBy">Order By: </label>
                          <select name="cmbOrderBy" id="cmbOrderBy">
                            <option selected="selected" value="<%=thisPage.getOrderById()%>"><%=thisPage.getOrderBy()%></option>
                            <option value=" "> </option>
							<option value="a_activity_list.deadline">Deadline</option>
                            <option value="a_activity.activity_name">Project Name</option>
                            <option value="a_activity_list.hrs_to_work-a_activity_list.hrs_worked">Remaining Hours</option>
                          </select>
					    </li>
						<li><input id="glbutton" type="submit" name="glbutton" value="Filter" /></li>
					</ul>
					</fieldset>
				</form>
			</div>
	  </div>
		<span class="RedIndicator">*</span> <em><span class="CommentStyle">Check the option to inform the administrator that you have finalized your work</span>.</em>
		<div id="table_listing">
			<div class="content">
				<form id="form3" method="post" action="/TMS/sFinalizeActivity">
				<a name="Filter" id="Filter"></a><a name="Save" id="Save"></a>
				<table width="100%" cellspacing="0" cellpadding="0">
                    <tr bgcolor="#3B3B3B">
                      <td width="21" class="RowHeader">&nbsp;</td>
                      <td width="276" class="RowHeader">Project Name</td>
                      <td width="106"><div align="right" class="RowHeader">Remaining Hours</div></td>
                      <td width="93"><div align="center" class="RowHeader">Deadline</div></td>
                    </tr>
                    <%
                        tmsclass.ProjectListing.queryAnalyzer _QA = new tmsclass.ProjectListing.queryAnalyzer();
                    %>
                    <%=_QA.showProjectListing(myUser.getUserId(), thisPage.getProjectStatusId(), thisPage.getProjectID(), thisPage.getOrderById())%>
              	</table>
                <input id="glbutton" type="submit" name="glbutton" value="Save" <%=myUser.checkAccess(constants.Actions.PROJECT_STATUS_MODIFICATION)%> />
                </form>
				<p align="right"><a href="../ReportPages/ActivityStatusReport.jsp">View Project Status</a></p>
			</div>
		</div>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li class="active"><a href="../../index.jsp" title="">Homepage</a></li>
				<li><a href="../../AboutUs.jsp" title="">About Us</a></li>
				<li><a href="#" title="">Projects</a></li>
				<li><a href="NonBillableTimeLog.jsp" title="">Leisure</a></li>
				<li><a href="../Leaves/LeaveTimeLog.jsp" title="">Work Leaves</a></li>
				<li><a href="../ReportPages/ReportSelection.jsp" title="">Reports</a></li>
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
		<div id="updates" class="boxed">
			<h2 class="title">Recent Updates</h2>
			<div class="content">
				<ul>
					<%
						tmsclass.WorkUtility.queryAnalyzer _QAUpdates = new tmsclass.WorkUtility.queryAnalyzer();
					%>
					<%=_QAUpdates.showUpdates()%>
				</ul>
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