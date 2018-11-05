<%-- 
    Document   : index
    Created on : Sep 22, 2009, 3:21:07 PM
    Author     : spring
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="mainCss.css" rel="stylesheet" type="text/css" media="screen" />
        <title>login</title>
    </head>
    <body>
        <h2>Login</h2>
        <div>
        <form method = "post" action="Servlet1" id="loginfrm">
            <input type="hidden" name="log" />
            <table>
                <tbody>
                    <tr>
                        <td>Username</td>
                        <td><input type="text" name="name" id="username" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" id="password" /></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" name="Login" value="Login" id="submit" /></td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
    </body>
</html>
