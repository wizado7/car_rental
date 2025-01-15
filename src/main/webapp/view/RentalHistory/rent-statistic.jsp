<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistics</title>
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
        }

        nav a {
            color: white;
            text-decoration: none;
            margin: 0 10px;
        }

        nav a:hover {
            text-decoration: underline;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
            border-radius: 8px;
        }

        h1 {
            text-align: center;
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
    <a href="/">Home</a>
    <a href="/cars">Cars</a>
    <a href="/clients">Clients</a>
    <a href="/violation">Violations</a>
    <a href="/rental/history">Rental History</a>
</nav>
<div class="container">
    <h1>Statistics</h1>

    <h2>Top 3 Most Rented Cars</h2>
    <table>
        <thead>
        <tr>
            <th>Car ID</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Rent Count</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="car" items="${topRentedCars}">
            <tr>
                <td>${car.id}</td>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>${car.dailyRate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>Total Rental Cost</h2>
    <p><strong>${totalCost}</strong></p>

    <h2>Most Frequent Client</h2>
    <c:if test="${mostFrequentClient != null}">
        <p><strong>${mostFrequentClient.fullName}</strong> (Client ID: ${mostFrequentClient.id})</p>
    </c:if>
    <c:if test="${mostFrequentClient == null}">
        <p>No clients found</p>
    </c:if>
</div>
</body>
</html>
