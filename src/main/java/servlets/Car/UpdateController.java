package servlets.Car;


import config.ConnectionManager;

import dataaccess.entity.Car;
import dataaccess.repository.CarRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/edit/car")
public class UpdateController extends HttpServlet {
    private final CarRepository carRepository = CarRepository.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdParam = req.getParameter("carId");


        try {
            int carId = Integer.parseInt(carIdParam);

            Car car = carRepository.findById(carId);


            req.setAttribute("car", car);
            req.getRequestDispatcher("/edit-car.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdParam = req.getParameter("id");
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        int year = Integer.parseInt(req.getParameter("year"));
        String registrationNumber = req.getParameter("registrationNumber");
        BigDecimal dailyRate = new BigDecimal(req.getParameter("dailyRate"));
        String status = req.getParameter("status");


        try {
            int carId = Integer.parseInt(carIdParam);

            Car car = new Car();
            car.setId(carId);
            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);
            car.setRegistrationNumber(registrationNumber);
            car.setDailyRate(dailyRate);
            car.setStatus(String.valueOf(status));

            carRepository.update(car);
            resp.sendRedirect("/cars");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID");
        }
    }
}
