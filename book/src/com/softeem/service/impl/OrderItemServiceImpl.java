package com.softeem.service.impl;

import com.softeem.bean.OrderItem;
import com.softeem.dao.OrderItemDao;
import com.softeem.dao.impl.OrderItemDaoImpl;
import com.softeem.service.OrderItemService;
import com.softeem.utils.Page;

import java.sql.SQLException;

public class OrderItemServiceImpl implements OrderItemService {
    OrderItemDao orderItemDao = new OrderItemDaoImpl();
    @Override
    public Page<OrderItem> page(Integer pageNo, Integer pageSize,String orderId) throws SQLException {
        Page<OrderItem> page = new Page<>();
        Integer totalCount = orderItemDao.pageCount(orderId);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize - 1) / pageSize);
        page.setPageNo(pageNo);//设置当前页
        page.setItems(orderItemDao.searchById(orderId,page.getPageNo()));//设置分页查询的结果集
        return page;
    }
}
