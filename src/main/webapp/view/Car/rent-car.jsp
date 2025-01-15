<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rent a Car</title>
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
<h1 style="text-align: center;">Rent a Car - ${car.brand} ${car.model}</h1>
<form action="${pageContext.request.contextPath}/rent/car" method="post">
    <input type="hidden" name="carId" value="${car.id}">

    <h3>Client Information</h3>
    <label for="fullName">Full Name</label>
    <input type="text" id="fullName" name="fullName" required>

    <label for="phoneNumber">Phone Number</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required>

    <label for="email">Email</label>
    <input type="email" id="email" name="email">

    <label for="passportNumber">Passport Number</label>
    <input type="text" id="passportNumber" name="passportNumber" required>

    <label for="address">Address</label>
    <input type="text" id="address" name="address">

    <h3>Rental Information</h3>
    <label for="rentalDate">Rental Date</label>
    <input type="date" id="rentalDate" name="rentalDate" required>

    <label for="returnDate">Return Date</label>
    <input type="date" id="returnDate" name="returnDate" required>

    <h3>Car Information</h3>
    <p><strong>Brand:</strong> ${car.brand}</p>
    <p><strong>Model:</strong> ${car.model}</p>
    <p><strong>Daily Rate:</strong> ${car.dailyRate}</p>

    <button type="submit">Rent This Car</button>
</form>
</body>
</html>
