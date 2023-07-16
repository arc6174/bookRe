package com.softeem.test;

import com.softeem.bean.OrderItem;
import com.softeem.dao.OrderItemDao;
import com.softeem.dao.impl.OrderItemDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

public class OrderItemDaoTest {
    @Test
    public void saveTest() throws SQLException{
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.save(new OrderItem(null,"java 从入门到精通", 1,new BigDecimal(100),new BigDecimal(100),"1111111"));
        orderItemDao.save(new OrderItem(null,"javaScript 从入门到精通", 2,new BigDecimal(100),new BigDecimal(200),"111111"));
        orderItemDao.save(new OrderItem(null,"Netty 入门", 1,new BigDecimal(100),new BigDecimal(100),"111111"));

    }
}
