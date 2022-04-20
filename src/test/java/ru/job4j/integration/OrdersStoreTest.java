package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/scripts/update_004.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "Order1", "desc1",
                new Timestamp(System.currentTimeMillis()));
        store.save(order);
        Order findByName = store.findByName(order.getName());
        assertThat(findByName.getDescription(), is(order.getDescription()));
    }

    @Test
    public void whenFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "Order1", "desc1",
                new Timestamp(System.currentTimeMillis()));
        store.save(order);
        assertThat(store.findById(1), is(order));
    }

    @Test
    public void whenUpdate() {
        OrdersStore store = new OrdersStore(pool);
        Order order = new Order(1, "Order1", "desc1",
                new Timestamp(System.currentTimeMillis()));
        store.save(order);
        Order newOrder = new Order(1, "Order2", "desc2",
                new Timestamp(System.currentTimeMillis()));
        store.update(newOrder);
        Order findById = store.findById(order.getId());
        assertThat(findById.getName(), is(newOrder.getName()));
        assertThat(findById.getDescription(), is(newOrder.getDescription()));
    }

    @After
    public void dropTableAfterTests() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE orders").executeUpdate();
    }
}