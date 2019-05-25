package com.anisa.uts.dao;

import com.anisa.uts.model.Barang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BarangDao {

    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public BarangDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public boolean insertBarang(Barang barang) throws SQLException {
        String sql = "INSERT INTO barang (kdbarang, nmbarang, merek, harga) VALUES (?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, barang.getKdbarang());
        statement.setString(2, barang.getNmbarang());
        statement.setString(3, barang.getMerek());
        statement.setDouble(4, barang.getHarga());

        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }

    public List<Barang> listAllBarang() throws SQLException {
        List<Barang> listBarang = new ArrayList<Barang>();

        String sql = "SELECT * FROM barang";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String kdbarang = resultSet.getString("kdbarang");
            String nmbarang = resultSet.getString("nmbarang");
            String merek = resultSet.getString("merek");
            double harga = resultSet.getDouble("harga");

            Barang barang = new Barang(id, kdbarang, nmbarang, merek, harga);
            listBarang.add(barang);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listBarang;
    }

    public boolean deleteBarang(Barang barang) throws SQLException {
        String sql = "DELETE FROM barang where id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, barang.getId());

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    public boolean updateBarang(Barang barang)  {
        String sql = "UPDATE barang SET kdbarang = ?, nmbarang = ?, merek = ?, harga = ?";
        sql += " WHERE id = ?";
        boolean rowUpdated = true;
        try {
            connect();

            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setString(1, barang.getKdbarang());
            statement.setString(2, barang.getNmbarang());
            statement.setString(3, barang.getMerek());
            statement.setDouble(4, barang.getHarga());
            statement.setInt(5, barang.getId());

            rowUpdated = statement.executeUpdate() > 0;
            statement.close();
            disconnect();
        } catch (SQLException e) {
            e.getErrorCode();
            e.printStackTrace();
            e.getSQLState();
        }
        return rowUpdated;
    }

    public Barang getBarang(int id) throws SQLException {
        Barang barang = null;
        String sql = "SELECT * FROM barang WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String kdbarang = resultSet.getString("kdbarang");
            String nmbarang = resultSet.getString("nmbarang");
            String merek = resultSet.getString("merek");
            double harga = resultSet.getDouble("harga");

            barang = new Barang(id, kdbarang, nmbarang, merek, harga);

        }

        resultSet.close();
        statement.close();

        return barang;
    }
}
