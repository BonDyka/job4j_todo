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

public class EditController extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EditController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        boolean done = Boolean.parseBoolean(req.getParameter("done"));
        try {
            ItemDao dao = new ItemDao();
            Item item = dao.read(id);
            item.setDone(done);
            dao.saveOrUpdate(item);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            LOG.error("Not updated!", e);
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
