<%-- 
    Document   : EditSlide
    Created on : Jun 18, 2024, 9:00:41 PM
    Author     : OwO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Slide</title>
</head>
<body>
    <h1>Edit Slide</h1>

    <form action="slideService" method="post" enctype="multipart/form-data">
        <input type="hidden" name="service" value="updateImage">
        <input type="hidden" name="sliderId" value="${sliderId}">
        <label for="image">Select Image:</label>
        <input type="file" id="image" name="image" accept="image/*"><br><br>
        <button type="submit">Upload Image</button>
    </form>
</body>
</html>
