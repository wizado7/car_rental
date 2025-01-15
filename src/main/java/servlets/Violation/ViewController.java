package servlets.Violation;

import dataaccess.entity.Violation;
import dataaccess.repository.ViolationRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/violation")
public class ViewController extends HttpServlet {
    private final ViolationRepository violationRepository = ViolationRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Violation> violations = violationRepository.findAll();
            req.setAttribute("violations", violations);
            req.getRequestDispatcher("/view-violation.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Ошибка при загрузке данных о нарушениях", e);
        }
    }
}
