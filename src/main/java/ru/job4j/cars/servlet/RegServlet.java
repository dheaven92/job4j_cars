package ru.job4j.cars.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;
import ru.job4j.cars.repository.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(RegServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            UserRepository userRepository = UserRepositoryImpl.instanceOf();
            User userWithSameEmail = userRepository.findUserByEmail(email);
            if (userWithSameEmail != null) {
                req.setAttribute("error", "Email занят другим пользователем");
                req.getRequestDispatcher("reg.jsp").forward(req, resp);
            } else {
                userRepository.createUser(
                        User.builder()
                                .name(name)
                                .email(email)
                                .password(password)
                                .build()
                );
                resp.sendRedirect(req.getContextPath() + "/login.do");
            }
        } catch (Exception e) {
            LOG.error("Could not register user", e);
            req.setAttribute("error", "Не удалось зарегистрироваться");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
