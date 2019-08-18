<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <title>Spring MVC Hello World</title>
</head>

<body>
<h2>All Employees in System</h2>

<table border="1">
    <tr>
        <th>Stock Symbol</th>
        <th>Shares</th>
        <th>Purchase Date</th>
    </tr>
    <c:forEach items="${stocks}" var="stock">
        <tr>
            <td>${stock.stockName}</td>
            <td>${stock.shares}</td>
            <td>${stock.date}</td>
        </tr>
    </c:forEach>
</table>
<h1><a href="/webapp/stocks/addstock">Add Stock</a></h1>
<ol>
    <li><h1><a href="/webapp/">Home</a></h1></li>
    <li><h1><a href="/webapp/stocks/500">Try error page</a></h1></li>
    <li><h1><a href="/webapp/stocks/notfound">Try not-found</a></h1></li>
</ol>
</body>
</html>
