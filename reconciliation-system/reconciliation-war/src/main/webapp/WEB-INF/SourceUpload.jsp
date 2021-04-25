<!ECOTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Source Upload</title>
</head>
<body>

<form method="post" action="/target-upload">
    <label for="source_name">
        Source Name:
    </label>
    <br>
    <input id="source_name" name="source_name" type="text">
    <br>
    <br>
    <label for="file_type">
        File Type:
    </label>
    <br>
    <select name="file_type">
        <option value="csv">CSV</option>
        <option value="json">JSON</option>
    </select>
    <br>
    <br>
    <input type="file" id="file" name="file">
    <br>
    <br>
    <input type="submit" value="next">
</form>

</body>
</html>