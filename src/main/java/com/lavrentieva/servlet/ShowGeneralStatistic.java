package com.lavrentieva.servlet;

import com.lavrentieva.model.StatsDTO;
import com.lavrentieva.service.DetailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "stats", value = "/stats")
public class ShowGeneralStatistic extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final StatsDTO stats = new DetailService().showStatistic();
        request.setAttribute("stats", stats);
        final List<String> allId = new DetailService().getAllId();
        request.setAttribute("allId", allId);
        request.getRequestDispatcher("/stats.jsp").forward(request, response);
    }
}
