package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataAccessObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO extends DataAccessObject<Order> {
    private final static String GET_BY_ID =
        "SELECT "
        + "  customer.first_name,"
        + "  customer.last_name, "
        + "  customer.email, "
        + "  orders.order_id, "
        + "  orders.creation_date, "
        + "  orders.total_due, "
        + "  orders.status, "
        + "  salesperson.first_name, "
        + "  salesperson.last_name, "
        + "  salesperson.email, "
        + "  order_item.quantity, "
        + "  product.code, "
        + "  product.name, "
        + "  product.size, "
        + "  product.variety, "
        + "  product.price "
        + "FROM "
        + "  orders "
            + "  JOIN customer on orders.customer_id = customer.customer_id "
        + "  JOIN salesperson"
        + "             on orders.salesperson_id = salesperson.salesperson_id "
        + "  JOIN order_item "
        + "             on order_item.order_id = orders.order_id "
        + "  JOIN product"
        + "             on order_item.product_id = product.product_id "
        + "where "
        + "  orders.order_id = ?;";


    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_BY_ID);){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            int iterationCount = 0;
            List<OrderLine> orderLines = new ArrayList<>();
            while(rs.next()){
                if(iterationCount==0){
                    order.setId(rs.getLong(4));
                    order.setCustomerFirstName(rs.getString(1));
                    order.setCustomerLastName(rs.getString(2));
                    order.setCustomerEmail(rs.getString(3));
                    order.setCreationDate(rs.getString(5));
                    order.setTotalDue(rs.getDouble(6));
                    order.setStatus(rs.getString(7));
                    order.setSalespersonFirstName(rs.getString(8));
                    order.setSalespersonLastName(rs.getString(9));
                    order.setSalespersonEmail(rs.getString(10));

                }
                OrderLine orderLine = new OrderLine();
                orderLine.setQuantity(rs.getInt(11));
                orderLine.setProductCode(rs.getString(12));
                orderLine.setProductName(rs.getString(13));
                orderLine.setProductSize(rs.getInt(14));
                orderLine.setProductVariety(rs.getString(15));
                orderLine.setProductPrice(rs.getDouble(16));
                orderLines.add(orderLine);
                iterationCount++;
            }
            order.setOrderLines(orderLines);
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
    }
    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {}
}
