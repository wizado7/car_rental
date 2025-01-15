<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Close Rent</title>
</head>
<body>
<h1>Close Rent</h1>
<form action="/rent/close" method="post">
    <input type="hidden" name="rentalHistoryId" value="${rentalHistory.id}">
    <input type="hidden" name="carId" value="${rentalHistory.carId}">
    <p>
        <label for="description">Violation Description:</label><br>
        <textarea id="description" name="description" rows="4" cols="50" required></textarea>
    </p>
    <p>
        <label for="fineAmount">Fine Amount:</label><br>
        <input type="number" id="fineAmount" name="fineAmount" step="0.01" required>
    </p>
    <p>
        <label for="dateOfViolation">Date of Violation:</label><br>
        <input type="date" id="dateOfViolation" name="dateOfViolation" required>
    </p>
    <button type="submit">Close Rent</button>
</form>
<a href="/rental/history">Back to Rental History</a>
</body>
</html>
