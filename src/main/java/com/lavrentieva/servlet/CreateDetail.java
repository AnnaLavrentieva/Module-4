package com.lavrentieva.servlet;

import com.lavrentieva.model.Detail;
import com.lavrentieva.service.DetailService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateDetail", value = "/start")
public class CreateDetail extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        final Detail detail = new DetailService().create();
        final PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<h2>Detail created:</h2>");
        out.println("<h4>" + detail + "</h4>");
        out.println("<form method='get' action='http://localhost:8080'>");
        out.println("<input type='submit' value='Main page'>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
