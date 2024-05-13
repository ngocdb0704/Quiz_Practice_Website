<%-- 
    Document   : My Registration
    Created on : 12 thg 5, 2024, 16:59:06
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="app.entity.Registration"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Registration</title>
    </head>
    <body>
       <table border="solid 1px">
        <tr>
            <th>id</th>
            <th>subject</th>
            <th>registration time</th>
            <th>subject package</th>
            <th>total cost</th>
            <th>status</th>
            <th>valid from</th>
            <th>valid to</th>
            <th>edit</th>
            <th>cancel</th>
        </tr>
        <%
            Vector<Registration> registrationVector = (Vector<Registration>) request.getAttribute("data");
            for(Registration regist:registrationVector){
            
        %>
        <tr>
            <td><%=regist.getId()%></td>
            <td><%=regist.getSubject()%></td>
            <td><%=regist.getRegistrationTime()%></td>
            <td><%=regist.getSubjectPackage()%></td>
            <td><%=regist.getTotalCost()%></td>
            <td><%=regist.getStatus()%></td>
            <td><%=regist.getValidFrom()%></td>
            <td><%=regist.getValidTo()%></td>
            <td><a href="RegistrationController?service=edit">edit</a></td>
            <td><a href="RegistrationController?service=cancel&id=<%=regist.getId()%>">cancel</a></td>
        </tr>
        <%}%>
    </table>
    </body>
</html>
