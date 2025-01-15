package servlets.RentalHistory;

import dataaccess.entity.Car;
import dataaccess.entity.Client;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/rent/statistics")
public class StatisticController extends HttpServlet {
    private final RentalHistoryRepository rentalHistoryRepository = RentalHistoryRepository.getInstance();
    private final CarRepository carRepository = CarRepository.getInstance();
    private final ClientRepository clientRepository = ClientRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<Integer, Long> carRentals = rentalHistoryRepository.findAll().stream()
                    .collect(Collectors.groupingBy(r -> r.getCarId(), Collectors.counting()));

            List<Map.Entry<Integer, Long>> topCars = carRentals.entrySet().stream()
                    .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                    .limit(3).toList();

            List<Car> topRentedCars = new ArrayList<>();
            for (Map.Entry<Integer, Long> entry : topCars) {
                Car car = carRepository.findById(entry.getKey());
                if (car != null) {
                    topRentedCars.add(car);
                }
            }

            BigDecimal totalCost = rentalHistoryRepository.findAll().stream()
                    .map(r -> r.getTotalCost())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<Integer, Long> clientRentals = rentalHistoryRepository.findAll().stream()
                    .collect(Collectors.groupingBy(r -> r.getClientId(), Collectors.counting()));

            Optional<Map.Entry<Integer, Long>> mostFrequentClientEntry = clientRentals.entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            Client mostFrequentClient = mostFrequentClientEntry.isPresent()
                    ? clientRepository.findById(mostFrequentClientEntry.get().getKey())
                    : null;

            req.setAttribute("topRentedCars", topRentedCars);
            req.setAttribute("totalCost", totalCost);
            req.setAttribute("mostFrequentClient", mostFrequentClient);

            req.getRequestDispatcher("/rent-statistic.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке статистики", e);
        }
    }
}
