<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Target Upload</title>
</head>
<body>

<h2>Prepare Your Target File</h2>
<br>

<form method="post" action="/summary" enctype="multipart/form-data">
    <label for="target_name">Target Name:</label>
    <br>
    <input id="target_name" name="target_name" type="text">
    <br>
    <br>
    <label for="target_type">File Type:</label>
    <br>
    <select id="target_type" name="target_type">
        <c:forEach items="${types}" var="type">
            <option value="${type}">${type}</option>
        </c:forEach>
    </select>
    <br>
    <br>
    <label for="target_file">File:</label>
    <br>
    <input id="target_file" name="target_file" type="file">
    <br>
    <br>
    <input type="submit" value="next">
    <br>
    <br>
</form>

<form method="get" action="/source-upload">
    <button type="submit">Previous</button>
</form>

<%@include file="fragments/logout.jsp"%>

</body>
</html>