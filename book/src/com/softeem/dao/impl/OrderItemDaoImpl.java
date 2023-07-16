package com.softeem.dao.impl;

import com.softeem.bean.OrderItem;
import com.softeem.dao.OrderItemDao;
import com.softeem.utils.BaseDao;
import com.softeem.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    @Override
    public void save(OrderItem orderItem) throws SQLException {
        Connection connection= JdbcUtils.getConnection();
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        queryRunner.update(connection,sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> findAll() throws SQLException {
        return null;
    }

    @Override
    public void updateById(OrderItem orderItem) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public OrderItem findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> searchById(String orderId, Integer pageNumber) throws SQLException {
        String sql = "select * from t_order_item where order_id = ? limit ?,?";
        BeanListHandler<OrderItem> handler = new BeanListHandler<>(OrderItem.class, processor);
        return queryRunner.query(sql, handler, orderId, (pageNumber - 1) * pageSize, pageSize);
    }

    @Override
    public Integer pageCount(String orderId) throws SQLException {
        ScalarHandler<Long> handler = new ScalarHandler<>();
        String sql = "select count(*) from t_order_item where order_id = ?";
        Long i = queryRunner.query(sql, handler, orderId);
        return i.intValue();
    }
}
