<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental History</title>
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
        .filter-form {
            background-color: #f9f9f9;
            padding: 15px;
            border: 1px solid #ddd;
            margin-bottom: 20px;
        }
        .filter-form label {
            display: block;
            margin-bottom: 5px;
        }
        .filter-form input {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .filter-form button {
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filter-form button:hover {
            background-color: #0056b3;
        }
        .pagination {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 20px;
            align-items: center;
        }

        .pagination a {
            text-decoration: none;
            padding: 8px 12px;
            border: 1px solid #ddd;
            background-color: #f4f4f4;
            color: #007bff;
            border-radius: 4px;
            min-width: 20px;
            text-align: center;
        }

        .pagination a:hover {
            background-color: #ddd;
        }

        .pagination .active {
            background-color: #007bff;
            color: white;
            pointer-events: none;
        }

        .pagination .ellipsis {
            padding: 0 5px;
        }
        .date-inputs {
            display: flex;
            gap: 10px;
        }

        .date-inputs input {
            width: 30%;
        }

    </style>
</head>
<body>
<nav>
    <div>
        <a href="/">Home</a>
        <a href="/cars">Cars</a>
        <a href="/clients">Clients</a>
        <a href="/violation">Violation</a>
        <a href="/rent/statistics">Statistic</a>
    </div>
    <div>
        <a href="/">Go Back</a>
    </div>
</nav>
<h1>Rental History</h1>

<div class="filter-form">
    <form method="get" action="/rental/history">
        <label for="clientName">Client Full Name</label>
        <input type="text" id="clientName" name="clientName" value="${filterParams.clientName[0]}" />

        <label for="carModel">Car Model</label>
        <input type="text" id="carModel" name="carModel" value="${filterParams.carModel[0]}" />

        <label for="rentalDate">Rental Date</label>
        <div class="date-inputs">
            <input type="number" name="rentalYear" placeholder="Year" value="${filterParams.rentalYear[0]}" />
            <input type="number" name="rentalMonth" placeholder="Month" value="${filterParams.rentalMonth[0]}" />
            <input type="number" name="rentalDay" placeholder="Day" value="${filterParams.rentalDay[0]}" />
        </div>

        <label for="returnDate">Return Date</label>
        <div class="date-inputs">
            <input type="number" name="returnYear" placeholder="Year" value="${filterParams.returnYear[0]}" />
            <input type="number" name="returnMonth" placeholder="Month" value="${filterParams.returnMonth[0]}" />
            <input type="number" name="returnDay" placeholder="Day" value="${filterParams.returnDay[0]}" />
        </div>

        <label for="totalCost">Total Cost</label>
        <input type="number" id="totalCost" name="totalCost" value="${filterParams.totalCost[0]}" />

        <button type="submit">Filter</button>
    </form>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Client Full Name</th>
        <th>Car Model</th>
        <th>Rental Date</th>
        <th>Return Date</th>
        <th>Total Cost</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="rentalHistory" items="${rentalHistoryList}">
        <tr>
            <td>${rentalHistory.id}</td>
            <td>${clients[rentalHistory.clientId].fullName}</td>
            <td>${cars[rentalHistory.carId].model}</td>
            <td>${rentalHistory.rentalDate}</td>
            <td>${rentalHistory.returnDate}</td>
            <td>${rentalHistory.totalCost}</td>
            <td>
                <c:choose>
                    <c:when test="${rentalHistory.status == 'Closed'}">
                        <a href="/rent/details?rentalHistoryId=${rentalHistory.id}">Closed</a>
                    </c:when>
                    <c:otherwise>
                        <a href="/rent/close?rentalHistoryId=${rentalHistory.id}&carId=${rentalHistory.carId}">Close rent</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">
    <c:if test="${currentPage > 1}">
        <a href="/rental/history?page=${currentPage - 1}&size=5">&lt;</a>
    </c:if>

    <c:if test="${startPage > 1}">
        <a href="/rental/history?page=1&size=5">1</a>
        <c:if test="${startPage > 2}">
            <span class="ellipsis">...</span>
        </c:if>
    </c:if>

    <c:forEach var="i" begin="${startPage}" end="${endPage}">
        <a href="/rental/history?page=${i}&size=5" class="${currentPage == i ? 'active' : ''}">${i}</a>
    </c:forEach>

    <c:if test="${endPage < totalPages}">
        <c:if test="${endPage < totalPages - 1}">
            <span class="ellipsis">...</span>
        </c:if>
        <a href="/rental/history?page=${totalPages}&size=5">${totalPages}</a>
    </c:if>

    <c:if test="${currentPage < totalPages}">
        <a href="/rental/history?page=${currentPage + 1}&size=5">&gt;</a>
    </c:if>
</div>
</body>
</html>
