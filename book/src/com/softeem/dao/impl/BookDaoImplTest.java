package com.softeem.dao.impl;

import com.softeem.bean.Book;
import com.softeem.dao.BookDao;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookDaoImplTest {

    @Test
    public void testQueryForPageTotalCount() throws SQLException {
        BookDao bookDao = new BookDaoImpl();
        List<Book> bookList = bookDao.queryForPageItems(0, 5, null, null, new BigDecimal(20), new BigDecimal(100));
        for (Book book : bookList) {
            System.out.println("book = " + book);
        }
    }

}