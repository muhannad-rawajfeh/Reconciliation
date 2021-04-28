<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title>Source Upload</title>
</head>
<body>

<h2>Prepare Your Source File</h2>
<br>

<form method="post" action="/target-upload" enctype="multipart/form-data">
    <label for="source_name">Source Name:</label>
    <br>
    <input id="source_name" name="source_name" type="text">
    <br>
    <br>
    <label for="source_type">File Type:</label>
    <br>
    <select id="source_type" name="source_type">
        <c:forEach items="${types}" var="type">
            <option value="${type}">${type}</option>
        </c:forEach>
    </select>
    <br>
    <br>
    <label for="source_file">File:</label>
    <br>
    <input id="source_file" name="source_file" type="file">
    <br>
    <br>
    <input type="submit" value="next">
</form>

<br>
<%@include file="fragments/logout.jsp"%>

</body>
</html>