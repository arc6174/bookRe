package com.softeem.dao.impl;

import com.softeem.bean.Book;
import com.softeem.dao.BookDao;
import com.softeem.utils.BaseDao;
import com.softeem.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {

    BeanProcessor bean = new GenerousBeanProcessor();
    RowProcessor processor = new BasicRowProcessor(bean);

    @Override
    public void save(Book tBook) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_book values(null,?,?,?,?,?,?)";
        queryRunner.update(connection,sql, tBook.getName(), tBook.getPrice(), tBook.getAuthor(), tBook.getSales(), tBook.getStock(), tBook.getImgPath());
    }

    @Override
    public List<Book> findAll() throws SQLException {
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class, processor);
        List<Book> List = queryRunner.query("select * from t_book order by id desc", handler);
        return List;
    }

    @Override
    public void updateById(Book tBook) throws SQLException {
//        queryRunner.update("update Book set price = ? where id = ?", tBook.getPrice(),tBook.getId());
        queryRunner.update("update t_book set name = ?,price = ?,author = ?,sales = ?,stock = ?,img_path = ? where id = ?",
                tBook.getName(), tBook.getPrice(), tBook.getAuthor(), tBook.getSales(), tBook.getStock(), tBook.getImgPath(), tBook.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        queryRunner.update("delete from t_book where id = ?", id);
    }

    @Override
    public Book findById(Integer id) throws SQLException {
        BeanHandler<Book> handler = new BeanHandler<>(Book.class, processor);
        return queryRunner.query("select * from t_book where id = ? ", handler, id);
    }

    @Override
    public List<Book> page(Integer pageNumber) throws SQLException {
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class);
        List<Book> List = queryRunner.query("select * from t_book limit ?,?", handler, (pageNumber - 1) * pageSize, pageSize);
        return List;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query("select count(*) from t_book", handler);
        return i.intValue();
    }

    /*
     *  查询book表的总记录数
     *  @return
     *  @throws SQlException
     * */
    @Override
    public Integer queryForPageTotalCount() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }

    /*
     * 分页查询
     * @param begin
     * @param pageSize
     * @return
     * @throws SQlException
     * */
    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) throws SQLException {
        String sql = "select * from t_book order by id desc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<Book>(Book.class, processor), begin, pageSize);
    }

    @Override
    public Integer queryForPageTotalCount(String name, String author, BigDecimal max, BigDecimal min) throws SQLException {
        StringBuilder sql = new StringBuilder("select count(*) from t_book where 1 = 1 ");
        ArrayList list = new ArrayList();
        if (name != null && !"".equals(name)) {
            sql.append(" and name like ? ");
            list.add("%" + name + "%");
        }
        if (author != null && !"".equals(author)) {
            sql.append(" and author like ? ");
            list.add("%" + author + "%");
        }
        if ((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min值 大于 max值
            if (min.compareTo(max) == 1) {
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price Between ? and ? ");
            list.add(min);
            list.add(max);
        } else if (min != null && min.signum() == 1) {
            sql.append(" and price > ?");
            list.add(min);
        } else if (max != null && max.signum() == 1) {
            sql.append(" and price < ?");
            list.add(max);
        }
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql.toString(), handler, list.toArray());
        return i.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal max, BigDecimal min) throws SQLException {
        StringBuilder sql = new StringBuilder("select * from t_book where 1 = 1 ");
        ArrayList list = new ArrayList();
        if (name != null && !"".equals(name)) {
            sql.append(" and name like ? ");
            list.add("%" + name + "%");
        }
        if (author != null && !"".equals(author)) {
            sql.append(" and author like ? ");
            list.add("%" + author + "%");
        }
        if ((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min值 大于 max值
            if (min.compareTo(max) == 1) {
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price Between ? and ? ");
            list.add(min);
            list.add(max);
        } else if (min != null && min.signum() == 1) {
            list.add(min);
            sql.append(" and price > ?");
        } else if (max != null && max.signum() == 1) {
            sql.append(" and price < ?");
            list.add(max);
        }
        String end = " order by id desc limit ?,?";
        sql.append(end);
        list.add(begin);
        list.add(pageSize);
        RowProcessor processor = new BasicRowProcessor();
        return queryRunner.query(sql.toString(), new BeanListHandler<>(Book.class, processor), list.toArray());
    }
}
