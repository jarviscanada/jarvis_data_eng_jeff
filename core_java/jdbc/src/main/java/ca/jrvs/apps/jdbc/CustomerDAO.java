package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {
    public CustomerDAO(Connection connection) {
        super(connection);
    }
    private static final String INSERT =
          "INSERT INTO customer (\n"
        + "  first_name, last_name, email, phone, \n"
        + "  address, city, state, zipcode\n"
        + ") \n"
        + "VALUES \n"
        + "  (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_ONE =
          "SELECT \n"
        + "  customer_id, \n"
        + "  first_name, \n"
        + "  last_name, \n"
        + "  email, \n"
        + "  phone, \n"
        + "  address, \n"
        + "  city, \n"
        + "  state, \n"
        + "  zipcode \n"
        + "FROM \n"
        + "  customer \n"
        + "WHERE \n"
        + "  customer_id = ?";

    private static final String UPDATE =
          "UPDATE \n"
        + "  customer \n"
        + "SET \n"
        + "  first_name = ?, \n"
        + "  last_name = ?, \n"
        + "  email = ?, \n"
        + "  phone = ?, \n"
        + "  address = ?, \n"
        + "  city = ?, \n"
        + "  state = ?, \n"
        + "  zipcode = ? \n"
        + "WHERE \n"
        + "  customer_id = ?;";

    private static final String DELETE =
          "DELETE FROM \n"
        + "  customer \n"
        + "WHERE \n"
        + "  customer_id = ?";

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setZipCode(rs.getString("zipcode"));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }
    @Override
    public Customer update(Customer dto) {
        Customer customer = null;
        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE);){
            SetStrings(dto, statement);
            statement.setLong(9, dto.getId());
            statement.execute();
            customer = this.findById(dto.getId());
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT);){
            SetStrings(dto, statement);
            statement.execute();
            int id = this.getLastVal(CUSTOMER_SEQUENCE);
            return this.findById(id);
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE);){
            statement.setLong(1, id);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void SetStrings(Customer dto, PreparedStatement statement) throws SQLException {
        statement.setString(1, dto.getFirstName());
        statement.setString(2, dto.getLastName());
        statement.setString(3, dto.getEmail());
        statement.setString(4, dto.getPhone());
        statement.setString(5, dto.getAddress());
        statement.setString(6, dto.getCity());
        statement.setString(7, dto.getState());
        statement.setString(8, dto.getZipCode());
    }
}
