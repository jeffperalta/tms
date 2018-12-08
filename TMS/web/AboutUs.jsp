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
<jsp:useBean  id="MessageManager" class="message.ErrorMessage" scope="session"/>

<%
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
			<h1 class="title">About Us!</h1>
			<div class="content"><img src="images/img4.jpg" alt="" width="120" height="120" class="left" />
				<p><strong>Essence</strong> is a free template from <a href="http://www.freecsstemplates.org/">Free CSS Templates</a> released under a <a href="http://creativecommons.org/licenses/by/2.5/">Creative Commons Attribution 2.5 License</a>. The photo is from <a href="http://www.pdphoto.org/">PDPhoto.org</a>. You're free to use it for both commercial or personal use. I only ask that you link back to my site in some way. <em>Enjoy :)</em></p>
			</div>
		</div>
	    <div id="example" class="post">
			<h2 class="title">Citrus Solutions V1.0</h2>
			<div class="content">
				<p>Help Support Copyright<span style="font-size: 10pt; font-family: Arial;"> ©</span> 2008 All Rights Reserved</p>
				<p>Acknowledgement. </p>
				<p>Managed By <strong><em><a href="mailto:rommel@newthinkersco.com">The Rocket Science</a></em></strong></p>
				<p>Developed By <em><strong><a href="mailto:jaydocperalta@gmail.com">Jo Jefferson Peralta</a></strong></em></p>
				<p>Designed By <em><strong><a href="http://www.freecsstemplates.org/">Free CSS Templates</a></strong></em></p>
				<p>&nbsp;</p>
				<blockquote>
					<p>&ldquo;Pellentesque tristique ante ut  risus. Quisque dictum. Integer nisl risus, sagittis convallis, rutrum  id, elementum congue, nibh. Suspendisse dictum porta lectus. Donec  placerat odio.&rdquo;</p>
				</blockquote>
				<h3>&nbsp;</h3>
		  </div>
		</div>
	</div>
	<div id="sidebar">
		<div id="menu">
			<ul>
				<li><a href="index.jsp" title="">Homepage</a></li>
				<li class="active"><a href="#" title="">About Us</a></li>
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