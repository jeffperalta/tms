
<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<script language="javascript" type="text/javascript">
    var url = window.location.href;
    var qparts = url.split("?");
    var query = qparts[1];
    var qsub = query.split("&&");
    var filename = qsub[0].split("=");
    var filelocation = qsub[1].split("=");
    Newsite = window.open(filename[1], filename[1]);
    window.location = filelocation[1];
    //window.location="/GAS/ModuleSelection/moduleselection.jsp";
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <title></title>
    </head>
    <body></body>
</html>
