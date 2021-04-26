<!ECOTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Summary</title>
</head>
<body>

<h2>Summary</h2>
<br>

<table style="width:25%">
  <tr>
    <th>File</th>
    <th>Name</th>
    <th>Type</th>
  </tr>
  <tr>
    <td>Source</td>
    <td>${sourceName}</td>
    <td>${sourceType}</td>
  </tr>
  <tr>
    <td>Target</td>
    <td>${targetName}</td>
    <td>${targetType}</td>
  </tr>
</table>
<br>

<form method="get" action="/results">
    <button type="submit">
        Compare
    </button>
</form>

<form method="get" action="/target-upload">
    <button type="submit">
        Previous
    </button>
</form>

<form method="get" action="/source-upload">
    <button type="submit">
        Cancel
    </button>
</form>

</body>
</html>