package servlets.RentalHistory;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/rental/history")
public class ViewController extends HttpServlet {
    private final RentalHistoryRepository rentalHistoryRepository = RentalHistoryRepository.getInstance();
    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final CarRepository carRepository = CarRepository.getInstance();

    private static final int DEFAULT_PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = Integer.parseInt(req.getParameter("page") == null ? "1" : req.getParameter("page"));
            int size = Integer.parseInt(req.getParameter("size") == null ? String.valueOf(DEFAULT_PAGE_SIZE) : req.getParameter("size"));
            int offset = (page - 1) * size;

            String clientName = req.getParameter("clientName");
            String carModel = req.getParameter("carModel");
            String rentalYear = req.getParameter("rentalYear");
            String rentalMonth = req.getParameter("rentalMonth");
            String rentalDay = req.getParameter("rentalDay");
            String returnYear = req.getParameter("returnYear");
            String returnMonth = req.getParameter("returnMonth");
            String returnDay = req.getParameter("returnDay");
            String totalCost = req.getParameter("totalCost");

            List<RentalHistory> rentalHistoryList = rentalHistoryRepository.findFiltered(
                    clientName, carModel, rentalYear, rentalMonth, rentalDay,
                    returnYear, returnMonth, returnDay, totalCost, offset, size
            );

            int totalRecords = rentalHistoryRepository.countFiltered(
                    clientName, carModel, rentalYear, rentalMonth, rentalDay,
                    returnYear, returnMonth, returnDay, totalCost
            );
            int totalPages = (int) Math.ceil((double) totalRecords / size);

            int startPage = Math.max(1, page - 2);
            int endPage = Math.min(totalPages, page + 2);

            Map<Integer, Client> clients = new HashMap<>();
            Map<Integer, Car> cars = new HashMap<>();

            for (RentalHistory rentalHistory : rentalHistoryList) {
                int clientId = rentalHistory.getClientId();
                if (!clients.containsKey(clientId)) {
                    clients.put(clientId, clientRepository.findById(clientId));
                }

                int carId = rentalHistory.getCarId();
                if (!cars.containsKey(carId)) {
                    cars.put(carId, carRepository.findById(carId));
                }
            }

            req.setAttribute("rentalHistoryList", rentalHistoryList);
            req.setAttribute("clients", clients);
            req.setAttribute("cars", cars);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("startPage", startPage);
            req.setAttribute("endPage", endPage);
            req.setAttribute("filterParams", req.getParameterMap());

            req.getRequestDispatcher("/view-rental-history.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке данных истории аренды", e);
        }
    }
}