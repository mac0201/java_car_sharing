package carsharing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface CompanyMapper {
    Company mapCompany(ResultSet rs) throws SQLException;
}
