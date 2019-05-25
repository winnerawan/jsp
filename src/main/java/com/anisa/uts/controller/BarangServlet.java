package com.anisa.uts.controller;

import com.anisa.uts.dao.BarangDao;
import com.anisa.uts.model.Barang;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BarangServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BarangDao barangDao;

    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        barangDao = new BarangDao(jdbcURL, jdbcUsername, jdbcPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/":
                    index(request, response);
                    break;
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertBarang(request, response);
                    break;
                case "/delete":
                    deleteBarang(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateBarang(request, response);
                    break;
                default:
                    listBarang(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listBarang(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Barang> list = barangDao.listAllBarang();
        request.setAttribute("list", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }

    private void index(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Barang> list = barangDao.listAllBarang();
        request.setAttribute("/", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Barang barang = barangDao.getBarang(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form.jsp");
        request.setAttribute("barang", barang);
        dispatcher.forward(request, response);

    }

    private void insertBarang(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String kdbarang = request.getParameter("kdbarang");
        String nmbarang = request.getParameter("nmbarang");
        String merek = request.getParameter("merek");
        double harga = Double.parseDouble(request.getParameter("harga"));

        Barang barang = new Barang(kdbarang, nmbarang, merek, harga);
        barangDao.insertBarang(barang);
        response.sendRedirect("list");
    }

    private void updateBarang(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String kdbarang = request.getParameter("kdbarang");
        String nmbarang = request.getParameter("nmbarang");
        String merek = request.getParameter("merek");
        double harga = Double.parseDouble(request.getParameter("harga"));

        Barang barang = new Barang(id, kdbarang, nmbarang, merek, harga);
        barangDao.updateBarang(barang);
        response.sendRedirect("list");
    }

    private void deleteBarang(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Barang barang = new Barang(id);
        barangDao.deleteBarang(barang);
        response.sendRedirect("list");

    }
}
