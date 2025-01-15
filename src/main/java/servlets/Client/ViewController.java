package servlets.Client;

import dataaccess.entity.Client;
import dataaccess.repository.ClientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/clients")
public class ViewController extends HttpServlet {
    private final ClientRepository clientRepository = ClientRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Client> clients = clientRepository.findAll();
            req.setAttribute("clients", clients);
            req.getRequestDispatcher("/view-clients.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке данных клиентов", e);
        }
    }
}
