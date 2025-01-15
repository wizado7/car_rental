package servlets.RentalHistory;

import dataaccess.entity.Car;
import dataaccess.entity.RentalHistory;
import dataaccess.entity.Violation;
import dataaccess.repository.CarRepository;
import dataaccess.repository.RentalHistoryRepository;
import dataaccess.repository.ViolationRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/rent/close")
public class CloseRentController extends HttpServlet {
    private final RentalHistoryRepository rentalHistoryRepository = RentalHistoryRepository.getInstance();
    private final CarRepository carRepository = CarRepository.getInstance();
    private final ViolationRepository violationRepository = ViolationRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int rentalHistoryId = Integer.parseInt(req.getParameter("rentalHistoryId"));
            RentalHistory rentalHistory = rentalHistoryRepository.findById(rentalHistoryId);

            if (rentalHistory == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "История не найдена");
                return;
            }

            req.setAttribute("rentalHistory", rentalHistory);
            req.getRequestDispatcher("/close-rent.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке формы", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int rentalHistoryId = Integer.parseInt(req.getParameter("rentalHistoryId"));
            int carId = Integer.parseInt(req.getParameter("carId"));
            RentalHistory rentalHistory = rentalHistoryRepository.findById(rentalHistoryId);
            Car car = carRepository.findById(carId);

            if (rentalHistory == null || car == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "История или машина не найдена");
                return;
            }

            String description = req.getParameter("description");
            BigDecimal fineAmount = new BigDecimal(req.getParameter("fineAmount"));
            LocalDate dateOfViolation = LocalDate.parse(req.getParameter("dateOfViolation"));

            Violation violation = new Violation();
            violation.setRentalId(rentalHistory.getId());
            violation.setDescription(description);
            violation.setFineAmount(fineAmount);
            violation.setDateOfViolation(dateOfViolation);
            violationRepository.save(violation);

            car.setStatus("Available");
            carRepository.update(car);

            rentalHistory.setStatus("Closed");
            rentalHistoryRepository.update(rentalHistory);

            resp.sendRedirect("/rental/history");
        } catch (Exception e) {
            throw new ServletException("Ошибка при закрытии аренды", e);
        }
    }
}
