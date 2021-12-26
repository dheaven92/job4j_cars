package ru.job4j.cars.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IndexServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(IndexServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Post> posts = PostRepositoryImpl.instanceOf().findAllWithCarAndUserAndOrderByCreated();
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
