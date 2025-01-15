package servlets.Car;


import dataaccess.repository.CarRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete/car")
public class DeleteController extends HttpServlet {
    private final CarRepository carRepository = CarRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdParam = req.getParameter("id");
        try {
            int clientId = Integer.parseInt(carIdParam);

            carRepository.delete(clientId);

            resp.sendRedirect(req.getContextPath() + "/cars");
        } catch (Exception e) {
            throw new ServletException("Ошибка при удалении автомобиля", e);
        }
    }
}
