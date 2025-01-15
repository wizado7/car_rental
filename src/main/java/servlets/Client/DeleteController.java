package servlets.Client;


import dataaccess.repository.CarRepository;
import dataaccess.repository.ClientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete/client")
public class DeleteController extends HttpServlet {
    private final ClientRepository clientRepository = ClientRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carIdParam = req.getParameter("id");
        try {
            int clientId = Integer.parseInt(carIdParam);

            clientRepository.delete(clientId);

            resp.sendRedirect(req.getContextPath() + "/clients");
        } catch (Exception e) {
            throw new ServletException("Ошибка при удалении клиента", e);
        }
    }

}
