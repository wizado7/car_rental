<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cars List</title>
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
        <a href="/clients">Clients</a>
        <a href="/rental/history">Rental History</a>
        <a href="/violation">Violation</a>
        <a href="/rent/statistics">Statistic</a>
    </div>
    <div>
        <a href="/">Go Back</a>
    </div>
</nav>

<h1>Cars List</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Brand</th>
        <th>Model</th>
        <th>Year</th>
        <th>Registration Number</th>
        <th>Daily Rate</th>
        <th>Status</th>
        <th>Actions</th>
        <th>Rent</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>${car.id}</td>
            <td>${car.brand}</td>
            <td>${car.model}</td>
            <td>${car.year}</td>
            <td>${car.registrationNumber}</td>
            <td>${car.dailyRate}</td>
            <td>${car.status}</td>
            <td>
                <a href="/edit/car?carId=${car.id}">Edit</a>
                <form action="/delete/car" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${car.id}">
                    <button class="button_delete" type="submit">Delete</button>
                </form>
            </td>
            <td>
                <c:choose>
                    <c:when test="${car.status == 'Rented' || car.status == 'Maintenance'}">
                        Unavailable
                    </c:when>
                    <c:otherwise>
                        <a href="/rent/car?carId=${car.id}">Rent this car</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/create/car">Add new car</a>
</body>
</html>
