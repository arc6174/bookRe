package com.softeem.test;


import com.softeem.bean.User;
import com.softeem.dao.UserDao;
import com.softeem.dao.impl.UserDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class UserDaoImplTest {

    private UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() throws SQLException {
        User user = userDao.queryUserByUsername("BY");
        System.out.println("user = " + user);
    }

    @Test
    public void queryUserByUsernameAndPassword() throws SQLException {
        User user = userDao.queryUserByUsernameAndPassword("admin", "admin");
        System.out.println("user = " + user);
    }

    @Test
    public void save() throws SQLException {
        User user = new User(null, "BY", "123456", "123qq.com");
        userDao.save(user);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void updateById() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void page() {
    }

    @Test
    public void pageRecord() {
    }
}