<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Results</title>
  <link href="../css/results-style.css" rel="stylesheet">
</head>
<body>

<h2>Reconciliation Results</h2>
<p>Click on the categories inside the tabbed menu:</p>

<div class="tab">
  <button class="tablinks" onclick="openTab(event, 'matched')">Matched</button>
  <button class="tablinks" onclick="openTab(event, 'mismatched')">Mismatched</button>
  <button class="tablinks" onclick="openTab(event, 'missing')">Missing</button>
</div>

<div id="matched" class="tabcontent">
  <table>
    <tr>
      <th>Transaction ID</th>
      <th>Amount</th>
      <th>Currency</th>
      <th>Date</th>
    </tr>

    <c:forEach items="${exportRequest.getMatched()}" var="transaction">
        <tr>
          <td>${transaction.getId()}</td>
          <td>${transaction.getAmount()}</td>
          <td>${transaction.getCurrency()}</td>
          <td>${transaction.getDate()}</td>
        </tr>
    </c:forEach>

  </table>
</div>

<div id="mismatched" class="tabcontent">
  <table>
    <tr>
      <th>Found in file</th>
      <th>Transaction ID</th>
      <th>Amount</th>
      <th>Currency</th>
      <th>Date</th>
    </tr>

    <c:forEach items="${exportRequest.getMismatched()}" var="sTransaction">
        <tr>
          <td>${sTransaction.getSource()}</td>
          <td>${sTransaction.getTransaction().getId()}</td>
          <td>${sTransaction.getTransaction().getAmount()}</td>
          <td>${sTransaction.getTransaction().getCurrency()}</td>
          <td>${sTransaction.getTransaction().getDate()}</td>
        </tr>
    </c:forEach>

  </table>
</div>

<div id="missing" class="tabcontent">
  <table>
    <tr>
      <th>Found in file</th>
      <th>Transaction ID</th>
      <th>Amount</th>
      <th>Currency</th>
      <th>Date</th>
    </tr>

    <c:forEach items="${exportRequest.getMissing()}" var="sTransaction">
        <tr>
          <td>${sTransaction.getSource()}</td>
          <td>${sTransaction.getTransaction().getId()}</td>
          <td>${sTransaction.getTransaction().getAmount()}</td>
          <td>${sTransaction.getTransaction().getCurrency()}</td>
          <td>${sTransaction.getTransaction().getDate()}</td>
        </tr>
    </c:forEach>

  </table>
</div>

<br>
<form method="post" action="/download">
    <label for="results_type">Result files format:</label>
    <br>
    <select id="results_type" name="results_type">
        <option value="CSV">CSV</option>
        <option value="JSON">JSON</option>
    </select>
    <br>
    <input type="submit" value="Download">
</form>

<br>
<form method="get" action="/source-upload">
    <button type="submit">Compare new files</button>
</form>

<br>
<%@include file="fragments/logout.jsp"%>

</body>
</html>

<script>
  function openTab(event, category) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(category).style.display = "block";
    event.currentTarget.className += " active";
  }
</script>