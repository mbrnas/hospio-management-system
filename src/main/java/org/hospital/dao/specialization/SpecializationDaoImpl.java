package org.hospital.dao.specialization;

import org.hospital.database.DatabaseConfig;
import org.hospital.model.specialization.Specialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecializationDaoImpl implements SpecializationDao {

    private final DataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(SpecializationDaoImpl.class);

    public SpecializationDaoImpl() {
        this.dataSource = DatabaseConfig.getDataSource();
    }

    public List<Specialization> getAllSpecializations() {
        List<Specialization> specializations = new ArrayList<>();
        String sql = "SELECT specialization_id, specialization_name FROM specializations";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Specialization specialization = new Specialization(
                        resultSet.getInt("specialization_id"),
                        resultSet.getString("specialization_name")
                );
                specializations.add(specialization);
            }
        } catch (SQLException e) {
            logger.error("Error getting all specializations", e);
        }
        return specializations;
    }

    @Override
    public Specialization getSpecializationById(int id) {
        String sql = "SELECT specialization_id, specialization_name FROM specializations WHERE specialization_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Specialization specialization = new Specialization(
                            resultSet.getInt("specialization_id"),
                            resultSet.getString("specialization_name")
                    );
                    return specialization;
                }
            }
        } catch (SQLException e) {
            logger.error("Error getting specialization by id", e);
        }
        return null;
    }

}
