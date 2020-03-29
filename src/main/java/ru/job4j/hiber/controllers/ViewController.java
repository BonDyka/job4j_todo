package ru.job4j.hiber.controllers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.hiber.models.Item;
import ru.job4j.hiber.persistence.ItemDao;
import ru.job4j.hiber.persistence.PersistException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewController extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        String answer;
        resp.setContentType("application/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        try {
            boolean showAll = Boolean.parseBoolean(req.getParameter("show"));
            List<Item> result = this.list(showAll);
            if (result.isEmpty()) {
                throw new Exception("Nothing to found.");
            }
            answer = new Gson().toJson(result);
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.append(answer).flush();
        } catch (Exception e) {
            LOG.error("Bad search result", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private List<Item> list(boolean all) throws PersistException {
        List<Item> result;
        if (!all) {
            result = new ItemDao().readUndone();
        } else {
            result = new ItemDao().readAll();
        }
        return result;
    }
}
