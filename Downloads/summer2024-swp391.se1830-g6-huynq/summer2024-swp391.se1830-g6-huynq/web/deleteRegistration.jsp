<%-- 
    Document   : deleteRegistration
    Created on : 13 thg 5, 2024, 17:08:26
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="app.entity.Registration"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Registration</title>
    </head>
    <body>
        <%Registration regist=(Registration)request.getAttribute("regist");%>
        <form action="RegistrationController" method = "post">
        <table>
            <tr>
                <td>id</td>
                <td><input type="text" name="rid" value="<%=regist.getId()%>" readonly></td>
            </tr>
            <tr>
                <td>subject</td>
                <td><input type="text" name="subject" value="<%=regist.getSubject()%>" readonly></td>
            </tr>
            <tr>
                <td>registration time</td>
                <td><input type="text" name="registTime" value="<%=regist.getRegistrationTime()%>" readonly></td>
            </tr>
            <tr>
                <td>subject package</td>
                <td><input type="text" name="package" value="<%=regist.getSubjectPackage()%>" readonly></td>
            </tr>
            <tr>
                <td>total cost</td>
                <td><input type="text" name="cost" value="<%=regist.getTotalCost()%>" readonly></td>
            </tr>
            <tr>
                <td>status</td>
                <td><input type="text" name="status" value="<<%=regist.getStatus()%>" readonly></td>
            </tr>
            <tr>
                <td>valid from</td>
                <td><input type="text" name="validFrom" value="<%=regist.getValidFrom()%>" readonly></td>
            </tr>
            <tr>
                <td>valid to</td>
                <td><input type="text" name="validTo" value="<%=regist.getValidTo()%>" readonly></td>
            </tr>
            <tr>
                <td><input type="submit" name = "cancel" value = "cancel"></td>
            </tr>
        </table>
            <input type="hidden" name = "service" value = "cancel">
    </form>
    </body>
</html>
