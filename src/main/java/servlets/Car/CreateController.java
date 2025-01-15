package servlets.Car;

import dataaccess.entity.Car;
import dataaccess.repository.CarRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/create/car")
public class CreateController extends HttpServlet {
    private final CarRepository carRepository = CarRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/create-car.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        int year = Integer.parseInt(req.getParameter("year"));
        String registrationNumber = req.getParameter("registrationNumber");
        BigDecimal dailyRate = new BigDecimal(req.getParameter("dailyRate"));
        String status = req.getParameter("status");
        try {

            Car car = new Car();
            car.setBrand(brand);
            car.setModel(model);
            car.setYear(year);
            car.setRegistrationNumber(registrationNumber);
            car.setDailyRate(dailyRate);
            car.setStatus(String.valueOf(status));


            carRepository.save(car);
            resp.sendRedirect(req.getContextPath() + "/cars");
        } catch (Exception e) {
            throw new ServletException("Ошибка при создании автомобиля", e);
        }
    }
}
