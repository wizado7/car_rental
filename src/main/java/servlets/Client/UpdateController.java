package servlets.Client;


import dataaccess.entity.Client;
import dataaccess.repository.ClientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/edit/client")
public class UpdateController extends HttpServlet {
    private final ClientRepository clientRepository = ClientRepository.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientIdParam = req.getParameter("clientId");


        try {
            int clientId = Integer.parseInt(clientIdParam);

            Client client = clientRepository.findById(clientId);


            req.setAttribute("client", client);
            req.getRequestDispatcher("/edit-client.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientIdParam = req.getParameter("id");
        String fullName = req.getParameter("fullName");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String passportNumber = req.getParameter("passportNumber");
        String address = req.getParameter("address");


        try {
            int clientId = Integer.parseInt(clientIdParam);

            Client client = new Client();
            client.setId(clientId);
            client.setFullName(fullName);
            client.setPhoneNumber(phoneNumber);
            client.setEmail(email);
            client.setPassportNumber(passportNumber);
            client.setAddress(address);


            clientRepository.update(client);
            resp.sendRedirect("/clients");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID");
        }
    }
}
