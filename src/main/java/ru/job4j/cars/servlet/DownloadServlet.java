package ru.job4j.cars.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class DownloadServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadServlet.class.getName());

    private final Properties properties = new Properties();

    @Override
    public void init() throws ServletException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(in);
        } catch (IOException e) {
            LOG.error("Could not read properties file", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        File downloadFile = null;
        String imagesFolder = properties.getProperty("path.images");
        for (File file : Objects.requireNonNull(new File(imagesFolder).listFiles())) {
            if (file.getName().contains(req.getParameter("id"))) {
                downloadFile = file;
                break;
            }
        }
        try (FileInputStream in = new FileInputStream(downloadFile)) {
            res.getOutputStream().write(in.readAllBytes());
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
        }
    }
}
