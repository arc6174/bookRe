package com.softeem.service;

import com.softeem.bean.OrderItem;
import com.softeem.utils.Page;

import java.sql.SQLException;

public interface OrderItemService {
    public Page<OrderItem> page(Integer pageNo, Integer pageSize,String orderId) throws SQLException;
}
