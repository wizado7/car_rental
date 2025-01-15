package servlets.Car;

import dataaccess.entity.Car;
import dataaccess.repository.CarRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class ViewController extends HttpServlet {
    private final CarRepository carRepository = CarRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Car> cars = carRepository.findAll();
            req.setAttribute("cars", cars);
            req.getRequestDispatcher("/view-cars.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке данных автомобилей", e);
        }
    }
}
