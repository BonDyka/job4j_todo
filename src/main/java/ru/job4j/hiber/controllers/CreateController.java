package ru.job4j.hiber.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hiber.models.Item;
import ru.job4j.hiber.persistence.ItemDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class CreateController extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(CreateController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
        Item item = new Item();
        item.setDescription(description);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        try {
            new ItemDao().saveOrUpdate(item);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            LOG.error("Not saved!", e);
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
