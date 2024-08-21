package org.hospital.dao.invoice;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDaoImpl implements InvoiceDao {

    private final DataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(InvoiceDaoImpl.class);

    public InvoiceDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT *   FROM invoices";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Invoice invoice = new Invoice(
                        resultSet.getInt("invoice_id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getTimestamp("invoice_date").toLocalDateTime(),
                        resultSet.getBigDecimal("amount"),
                        resultSet.getBoolean("paid")
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            logger.error("Error getting all invoices", e);
        }
        logger.info("Returned all invoices");
        return invoices;
    }

    @Override
    public Invoice getInvoiceById(int id) {
        String sql = "SELECT * FROM invoices WHERE invoice_id = ?";
        Invoice invoice = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    invoice = new Invoice(
                            resultSet.getInt("invoice_id"),
                            resultSet.getInt("patient_id"),
                            resultSet.getTimestamp("invoice_date").toLocalDateTime(),
                            resultSet.getBigDecimal("amount"),
                            resultSet.getBoolean("paid")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting invoice by id", e);
        }
        logger.info("Returned invoice by id {}", id);
        return invoice;
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoices (patient_id, invoice_date, amount, paid) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, invoice.getPatientId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(invoice.getInvoiceDate()));
            preparedStatement.setBigDecimal(3, invoice.getAmount());
            preparedStatement.setBoolean(4, invoice.getPaid());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error saving invoice", e);
        }
        logger.info("Successfully saved invoice");
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        String sql = "UPDATE invoices SET patient_id = ?, invoice_date = ?, amount = ?, paid = ? WHERE invoice_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, invoice.getPatientId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(invoice.getInvoiceDate()));
            preparedStatement.setBigDecimal(3, invoice.getAmount());
            preparedStatement.setBoolean(4, invoice.getPaid());
            preparedStatement.setInt(5, invoice.getInvoiceId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating invoice failed, no rows affected.");
            }
        } catch (SQLException e) {
            logger.error("Error updating invoice", e);
        }
        logger.info("Updated invoice with id {}", invoice.getInvoiceId());
    }

    @Override
    public void deleteInvoice(int id) {
        String sql = "DELETE FROM invoices WHERE invoice_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting invoice", e);
        }
        logger.info("Successfully deleted invoice with id {}", id);
    }
}
