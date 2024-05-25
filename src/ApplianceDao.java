import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplianceDao {
    public boolean addAppliance(Appliance appliance) {
        String sql = "INSERT INTO appliances (name, type, rating, consumption, hours_per_day, days_per_month, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appliance.getName());
            stmt.setString(2, appliance.getType());
            stmt.setInt(3, appliance.getRating());
            stmt.setDouble(4, appliance.getConsumption());
            stmt.setInt(5, appliance.getHoursPerDay());
            stmt.setInt(6, appliance.getDaysPerMonth());
            stmt.setInt(7, appliance.getUserId());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAppliance(Appliance appliance) {
        String sql = "UPDATE appliances SET name = ?, type = ?, rating = ?, consumption = ?, hours_per_day = ?, days_per_month = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appliance.getName());
            stmt.setString(2, appliance.getType());
            stmt.setInt(3, appliance.getRating());
            stmt.setDouble(4, appliance.getConsumption());
            stmt.setInt(5, appliance.getHoursPerDay());
            stmt.setInt(6, appliance.getDaysPerMonth());
            stmt.setInt(7, appliance.getId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Appliance> getAppliancesByUserId(int userId) {
        List<Appliance> appliances = new ArrayList<>();
        String sql = "SELECT * FROM appliances WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appliance appliance = new Appliance();
                appliance.setId(rs.getInt("id"));
                appliance.setName(rs.getString("name"));
                appliance.setType(rs.getString("type"));
                appliance.setRating(rs.getInt("rating"));
                appliance.setConsumption(rs.getDouble("consumption"));
                appliance.setHoursPerDay(rs.getInt("hours_per_day"));
                appliance.setDaysPerMonth(rs.getInt("days_per_month"));
                appliance.setUserId(rs.getInt("user_id"));
                appliances.add(appliance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appliances;
    }

    public boolean deleteAppliance(int applianceId) {
        String sql = "DELETE FROM appliances WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, applianceId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
