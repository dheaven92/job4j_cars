package ru.job4j.cars.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.BodyTypeRepositoryImpl;
import ru.job4j.cars.repository.BrandRepositoryImpl;
import ru.job4j.cars.repository.PostRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(PostServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("delete");
        String id = req.getParameter("id");
        User user = (User) req.getSession().getAttribute("user");
        Post post = null;

        if (id != null) {
            post = PostRepositoryImpl.instanceOf().findById(Integer.parseInt(id));
            if (post == null || post.getUser().getId() != user.getId()) {
                resp.sendRedirect(req.getContextPath() + "/404.jsp");
                return;
            }
        }

        if (delete != null && post != null) {
            PostRepositoryImpl.instanceOf().delete(post);
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.setAttribute("brands", BrandRepositoryImpl.instanceOf().findAll());
        req.setAttribute("bodyTypes", BodyTypeRepositoryImpl.instanceOf().findAll());
        req.setAttribute("post", post);
        req.getRequestDispatcher("post_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");

        try {
            String description = req.getParameter("description");
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String bodyType = req.getParameter("bodyType");
            String sold = req.getParameter("sold");

            Post post;

            if (id != null) {
                post = PostRepositoryImpl.instanceOf().findById(Integer.parseInt(req.getParameter("id")));
                if (post == null) {
                    throw new IllegalArgumentException("Could not find a post");
                }
                post.setSold(sold != null);
                post.setDescription(description);
                if (post.getCar() != null) {
                    post.getCar().setBrandId(Integer.parseInt(brand));
                    post.getCar().setModel(model);
                    post.getCar().setBodyTypeId(Integer.parseInt(bodyType));
                }
            } else {
                User user = (User) req.getSession().getAttribute("user");
                Car car = Car.builder()
                        .brandId(Integer.parseInt(brand))
                        .model(model)
                        .bodyTypeId(Integer.parseInt(bodyType))
                        .build();
                post = Post.builder()
                        .description(description)
                        .car(car)
                        .user(user)
                        .build();
            }

            PostRepositoryImpl.instanceOf().savePost(post);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e) {
            LOG.error("Could not create/update a post", e);
            req.setAttribute("error", id != null ? "Не удалось обновить объявление" : "Не удалось создать объявление");
            req.getRequestDispatcher("post_form.jsp").forward(req, resp);
        }
    }
}
