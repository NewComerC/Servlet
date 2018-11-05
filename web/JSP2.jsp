<%-- 
    Document   : JSP2
    Created on : Sep 22, 2009, 3:39:59 PM
    Author     : spring
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>logInfo</title>
        <link href="newCss.css" rel="stylesheet" type="text/css" media="screen" />
        <jsp:useBean id="ub" class="mbs.myBean" scope="session" />
    </head>
    <body>

        <h2>Welcome
            <jsp:getProperty name="ub" property="name" />
        </h2>
        <p>Current log record: 
        </p>
        <%
            List list = null;
            //get the log record from session
            if (session.getAttribute("list") != null) {
                list = (List) session.getAttribute("list");
                int l = list.size();
                if (l > 0) {
                    //if there is more than one record,create a table
        %>
        <table border="1">
            <tr>
                <th>opeId</th>
                <th>userId</th>
                <th>name</th>
                <th>status</th>
                <th>time</th>
            </tr>

            <%
                for (int i = 0; i < l; i++) {
                    HashMap<String, String> news = (HashMap) list.get(i);
            %>
            <tr>
                <td><%=news.get("opeId")%></td>
                <td><%=news.get("id")%></td>
                <td><%=news.get("name")%></td>
                <td><%=news.get("status")%></td> 
                <td><%=news.get("time")%></td>  
            </tr>

            <%
                }
            %>
        </table>
        <%
        } else {
            // no login record
        %>
        <p>no login record</p>
        <%
                }
            }
        %>
    </body>
</html>
