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
        <a href="/cars">Cars</a>
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
        <th>Name</th>
        <th>Phone number</th>
        <th>email</th>
        <th>Passport</th>
        <th>Address</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td>${client.id}</td>
            <td>${client.fullName}</td>
            <td>${client.phoneNumber}</td>
            <td>${client.email}</td>
            <td>${client.passportNumber}</td>
            <td>${client.address}</td>
            <td>
                <a href="/edit/client?clientId=${client.id}">Edit</a>
                <form action="/delete/client" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${client.id}">
                    <button class="button_delete" type="submit">Delete</button>
                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/create/client">add new client</a>
</body>
</html>
