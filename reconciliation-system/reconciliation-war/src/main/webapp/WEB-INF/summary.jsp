<!ECOTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Summary</title>
</head>
<body>

<h2>Summary</h2>
<br>

${sourceName} ---- ${sourceType}
<br>
<br>
${targetName} ---- ${targetType}
<br>
<br>
<br>

<form method="get" action="/results">
    <button type="submit">
        Compare
    </button>
</form>

<form method="get" action="/target-upload">
    <button type="submit">
        Back
    </button>
</form>

<form method="get" action="/source-upload">
    <button type="submit">
        Cancel
    </button>
</form>

</body>
</html>