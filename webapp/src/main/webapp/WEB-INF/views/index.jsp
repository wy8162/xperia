<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome</title>
</head>
<body>
<div>
    Welcome to my Web App based on Spring Web MVC.
    <h1> ${greeting} </h1>
    <p> ${tagline} </p>
</div>
<table>
    <tr>
        <td><a href="/webapp/stocks">Stock List Pages</a></td>
    </tr>
    <tr>
        <td>
            <a href="/webapp/employees">Employee List Pages</a>
        </td>
    </tr>
</table>
</body>
</html>