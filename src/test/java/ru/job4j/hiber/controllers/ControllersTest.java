package ru.job4j.hiber.controllers;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ControllersTest {
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private ServletOutputStream out = mock(ServletOutputStream.class);

    @Before
    public void setUp() throws Exception {
        when(resp.getOutputStream()).thenReturn(out);
    }

    @Test
    public void whenCalledViewThenItShouldTakeRequestParameterShow() throws ServletException, IOException {
        when(req.getParameter("show")).thenReturn("true");
        new ViewController().doGet(req, resp);
        verify(resp, atLeastOnce()).getOutputStream();
        verify(req, atLeastOnce()).getParameter("show");
    }

    @Test
    public void whenCreateCalledThenShouldTakeRequestParameterDescription() throws ServletException, IOException {
        when(req.getParameter("show")).thenReturn("Servlet test description");
        new CreateController().doPost(req, resp);
        verify(req, atLeastOnce()).getParameter("description");
    }

    @Test
    public void whenCalledEditThenItShouldTakeTwoRequestParameters() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("done")).thenReturn("true");
        new EditController().doPost(req, resp);
        verify(req, atLeastOnce()).getParameter("id");
        verify(req, atLeastOnce()).getParameter("done");
    }
}