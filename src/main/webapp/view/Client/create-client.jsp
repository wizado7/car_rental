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
<h1 style="text-align: center;">Create New Client</h1>
<form action="${pageContext.request.contextPath}/create/client" method="post">
    <label for="fullName">Full name</label>
    <input type="text" id="fullName" name="fullName" required>

    <label for="phoneNumber">Phone number</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required>

    <label for="email">email</label>
    <input type="email" id="email" name="email" required>

    <label for="passportNumber">Passport number</label>
    <input type="text" id="passportNumber" name="passportNumber" required>

    <label for="address">Address</label>
    <input type="text" id="address" name="address" required>


    <button type="submit">Create Car</button>
</form>
</body>
</html>
