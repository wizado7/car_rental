<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Violations</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        nav {
            background-color: #333;
            color: white;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        nav a {
            color: white;
            text-decoration: none;
            margin: 0 10px;
        }

        nav a:hover {
            text-decoration: underline;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
            text-align: left;
        }

        tr:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
<nav>
    <div>
        <a href="/">Home</a>
        <a href="/cars">Cars</a>
        <a href="/clients">Clients</a>
        <a href="/rental/history">Rental History</a>
        <a href="/rent/statistics">Statistic</a>
    </div>
    <div>
        <a href="/">Go Back</a>
    </div>
</nav>

<h1>Violations</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Rental ID</th>
        <th>Description</th>
        <th>Fine Amount</th>
        <th>Date of Violation</th>
        <th>Details</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="violation" items="${violations}">
        <tr>
            <td>${violation.id}</td>
            <td>${violation.rentalId}</td>
            <td>${violation.description}</td>
            <td>${violation.fineAmount}</td>
            <td>${violation.dateOfViolation}</td>
            <td>
                <a href="/rent/details?rentalHistoryId=${violation.rentalId}">View Details</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
