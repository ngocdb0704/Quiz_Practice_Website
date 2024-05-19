<%-- 
    Document   : EditUser
    Created on : May 18, 2024, 9:07:32 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector,entity.Customer,java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%Vector<Customer> vector
                    =(Vector<Customer>)request.getAttribute("vector");
        Customer cus =vector.get(0);
        %>
        <form action="CustomerControllerURL?service=listAll" method="post">
  <table>
      <caption>Edit User</caption>
     
      <tr>
          <td>ID</td>
          <td><input type="text" name="ID" value="<%=cus.getUserID()%>"></td>
      </tr>
      <tr>
          <td>Email</td>
          <td><input type="text" name="Email" id="" value="<%=cus.getEmail()%>"></td>
      </tr>
      <tr>
          <td>Password</td>
          <td><input type="text" name="Password" id="" value="<%=cus.getPassword()%>"></td>
      </tr>
      <tr>
          <td>Role</td>
          <td><input type="text" name="Role" id="" value="<%=cus.getRole()%>"></td>
      </tr>
      <tr>
          <td>FullName</td>
          <td><input type="text" name="FullName" id="" value="<%=cus.getFullName()%>"></td>
      </tr>
      <tr>
          <td>Gender</td>
          <td><input type="text" name="Gender" id="" value="<%=cus.getGender()%>"></td>
      </tr>
      <tr>
          <td>Mobile</td>
          <td><input type="text" name="Mobile" id="" value="<%=cus.getMobile()%>"></td>
      </tr>
      <tr>
          <td>isActive</td>
          <td>
              <td><input type="radio" name="isActive" value="1"
                <%=(cus.isIsActive()==true?"checked":"")%>>Active
                    <input type="radio" name="Discontinued" value="0"
                <%=(cus.isIsActive()==false?"checked":"")%>>Not Active
                </td>
          </td>
      </tr>
      <tr>
          <td><input type="submit" value="update User" name="submit"></td>
          <td><input type="reset" value="Reset"></td>
      </tr>
      <input type="hidden" name="service" value="update">

  </table>
</form>
    </body>
</html>
