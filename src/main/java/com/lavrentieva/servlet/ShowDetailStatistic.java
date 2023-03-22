package com.lavrentieva.servlet;

import com.lavrentieva.model.Detail;
import com.lavrentieva.service.DetailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "statsByDetail", value = "/stats/*")
public class ShowDetailStatistic extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String id = request.getParameter("id");
        final Detail detail = new DetailService().getDetailById(id);
        request.setAttribute("detail", detail);
        request.getRequestDispatcher("/statsDetail.jsp").forward(request, response);
    }
}
