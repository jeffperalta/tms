<%-- 
    Document   : ForwardPage
    Created on : Feb 17, 2009, 11:23:38 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<script>
    
    var url = window.location.href;
    var qparts = url.split("?");
    var query = qparts[1];
    var qsub = query.split("&&");
    var filename = qsub[0].split("=");
    var filelocation = qsub[1].split("=");
    Newsite = window.open(filename[1],"newsite");
    window.location = filelocation[1];
    //"/TMS/webpages/ReportPages/redirectme.jsp?code="+ _Parameter + "&&direct=" + "/TMS/webpages/ReportPages/ReportSelection.jsp"
   
    function bustOut(){
       var newWin = window.open(Newsite,"subWindow","height=500,width=700,resizable=yes,scrollbars=yes"); 
    }
</script>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="bustOut()">
     
    </body>
</html>
