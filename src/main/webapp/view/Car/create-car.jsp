<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Car</title>
    <style>
        form {
            width: 50%;
            margin: auto;
        }
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input, select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
        }
        button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<h1 style="text-align: center;">Create New Car</h1>
<form action="${pageContext.request.contextPath}/create/car" method="post">
    <label for="brand">Brand</label>
    <input type="text" id="brand" name="brand" required>

    <label for="model">Model</label>
    <input type="text" id="model" name="model" required>

    <label for="year">Year</label>
    <input type="number" id="year" name="year" min="1900" max="2100" required>

    <label for="registrationNumber">Registration Number</label>
    <input type="text" id="registrationNumber" name="registrationNumber" required>

    <label for="dailyRate">Daily Rate</label>
    <input type="number" id="dailyRate" name="dailyRate" step="0.01" required>

    <label for="status">Status</label>
    <select id="status" name="status" required>
        <option value="Available">Available</option>
        <option value="Rented">Rented</option>
        <option value="Maintenance">Maintenance</option>
    </select>

    <button type="submit">Create Car</button>
</form>
</body>
</html>
