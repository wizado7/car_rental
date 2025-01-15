package servlets.Client;

import dataaccess.entity.Client;
import dataaccess.repository.ClientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/create/client")
public class CreateController extends HttpServlet {
    private final ClientRepository clientRepository = ClientRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/create-client.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String passportNumber = req.getParameter("passportNumber");
        String address = req.getParameter("address");
        try {

            Client client = new Client();
            client.setFullName(fullName);
            client.setPhoneNumber(phoneNumber);
            client.setEmail(email);
            client.setPassportNumber(passportNumber);
            client.setAddress(address);


            clientRepository.save(client);
            resp.sendRedirect("/clients");

        } catch (NumberFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}