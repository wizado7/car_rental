package servlets.Car;

import dataaccess.entity.Car;
import dataaccess.entity.Client;
import dataaccess.entity.RentalHistory;
import dataaccess.repository.CarRepository;
import dataaccess.repository.ClientRepository;
import dataaccess.repository.RentalHistoryRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@WebServlet("/rent/car")
public class RentController extends HttpServlet {
    private final CarRepository carRepository = CarRepository.getInstance();
    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final RentalHistoryRepository rentalHistoryRepository = RentalHistoryRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int carId = Integer.parseInt(req.getParameter("carId"));
        Car car = carRepository.findById(carId);

        if (car != null) {
            req.setAttribute("car", car);
            req.getRequestDispatcher("/rent-car.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String fullName = req.getParameter("fullName");
            String phoneNumber = req.getParameter("phoneNumber");
            String email = req.getParameter("email");
            String passportNumber = req.getParameter("passportNumber");
            String address = req.getParameter("address");
            LocalDate rentalDate = LocalDate.parse(req.getParameter("rentalDate"));
            LocalDate returnDate = LocalDate.parse(req.getParameter("returnDate"));


            int carId = Integer.parseInt(req.getParameter("carId"));
            Car car = carRepository.findById(carId);

            Client client = clientRepository.findByPassportNumber(passportNumber);
            if (client == null) {
                client = new Client();
                client.setFullName(fullName);
                client.setPhoneNumber(phoneNumber);
                client.setEmail(email);
                client.setPassportNumber(passportNumber);
                client.setAddress(address);
                clientRepository.save(client);
            }

            long daysBetween = ChronoUnit.DAYS.between(rentalDate, returnDate);
            BigDecimal totalCost = car.getDailyRate().multiply(BigDecimal.valueOf(daysBetween));

            RentalHistory rentalHistory = new RentalHistory();
            rentalHistory.setClientId(client.getId());
            rentalHistory.setCarId(car.getId());
            rentalHistory.setRentalDate(rentalDate);
            rentalHistory.setReturnDate(returnDate);
            rentalHistory.setTotalCost(totalCost);
            rentalHistory.setStatus("Open");
            rentalHistoryRepository.save(rentalHistory);


            car.setStatus("Rented");
            carRepository.update(car);


            resp.sendRedirect(req.getContextPath() + "/cars");
        } catch (Exception e) {
            throw new ServletException("Ошибка при оформлении аренды", e);
        }
    }
}
