<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<html>
<head>
    <title>Add Employee Form</title> 
    <style>
    .error
    {
        color: #ff0000;
        font-weight: bold;
    }
    </style>
</head>
 
<body>
    <h2><spring:message code="stock.page" text="Add Stock" /></h2>
    <br/>
    <form:form method="post" modelAttribute="stock">
        <%-- <form:errors path="*" cssClass="error" /> --%>
        <table>
            <tr>
                <td><spring:message code="stock.stockName" text="Stock Name" /></td>
                <td><form:input path="stockName" /></td>
                <td><form:errors path="stockName" cssClass="error" /></td>
            </tr>
            <tr>
                <td><spring:message code="stock.shares" text="Shares" /></td>
                <td><form:input path="shares" /></td>
                <td><form:errors path="shares" cssClass="error" /></td>
            </tr>
            <tr>
                <td><spring:message code="stock.date" text="Date" /></td>
                <td><form:input type="date" path="date" /></td>
                <td><form:errors path="date" cssClass="error" /></td>
            </tr>
            <tr>
                <td colspan="3"><input type="submit" value="Add Stock"/></td>
            </tr>
        </table>
    </form:form>
</body>
</html>