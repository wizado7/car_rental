package servlets.Violation;

import dataaccess.entity.Car;
import dataaccess.entity.Client;
import dataaccess.entity.RentalHistory;
import dataaccess.entity.Violation;
import dataaccess.repository.CarRepository;
import dataaccess.repository.ClientRepository;
import dataaccess.repository.RentalHistoryRepository;
import dataaccess.repository.ViolationRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/rent/details")
public class DetailsController extends HttpServlet {
    private final RentalHistoryRepository rentalHistoryRepository = RentalHistoryRepository.getInstance();
    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final CarRepository carRepository = CarRepository.getInstance();
    private final ViolationRepository violationRepository = ViolationRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int rentalHistoryId = Integer.parseInt(req.getParameter("rentalHistoryId"));

            RentalHistory rentalHistory = rentalHistoryRepository.findById(rentalHistoryId);
            if (rentalHistory == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Rental history not found");
                return;
            }

            Client client = clientRepository.findById(rentalHistory.getClientId());
            Car car = carRepository.findById(rentalHistory.getCarId());

            Violation violation = violationRepository.findByRentalId(rentalHistoryId);

            req.setAttribute("rentalHistory", rentalHistory);
            req.setAttribute("client", client);
            req.setAttribute("car", car);
            req.setAttribute("violation", violation);

            req.getRequestDispatcher("/view-violation-from-rent.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error loading rental details", e);
        }
    }
}
