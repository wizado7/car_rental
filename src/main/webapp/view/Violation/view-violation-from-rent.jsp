<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental Details</title>
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
        <a href="javascript:history.back()">Go Back</a>
    </div>
</nav>
<h1>Rental Details</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Client Full Name</th>
        <th>Car Model</th>
        <th>Rental Date</th>
        <th>Return Date</th>
        <th>Total Cost</th>
        <th>Violation Description</th>
        <th>Fine Amount</th>
        <th>Date of Violation</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${rentalHistory.id}</td>
        <td>${client.fullName}</td>
        <td>${car.model}</td>
        <td>${rentalHistory.rentalDate}</td>
        <td>${rentalHistory.returnDate}</td>
        <td>${rentalHistory.totalCost}</td>
        <td>${violation.description}</td>
        <td>${violation.fineAmount}</td>
        <td>${violation.dateOfViolation}</td>
    </tr>
    </tbody>
</table>
<a href="/rental/history">Back to Rental History</a>
</body>
</html>
