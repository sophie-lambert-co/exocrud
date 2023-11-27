package com.package1.servlets;

import com.package1.database.Main;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CouleurServlets extends HttpServlet {

    private final Main main = new Main();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String colorsJson = main.getAllColorsAsJson();
        response.setContentType("exocrud/json");
        response.getWriter().write(colorsJson);
    }
}

