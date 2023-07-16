package com.softeem.test;

import com.softeem.bean.Order;
import com.softeem.dao.OrderDao;
import com.softeem.dao.impl.OrderDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDaoImplTest {
    @Test
    public void saveTest() throws SQLException {
        Order order = new Order("111111",new Timestamp(System.currentTimeMillis()),new BigDecimal(10),0,1);
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.save(order);
    }
}
