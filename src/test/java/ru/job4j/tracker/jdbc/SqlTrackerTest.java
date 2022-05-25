package ru.job4j.tracker.jdbc;

import org.junit.*;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class SqlTrackerTest {

    private static Connection connection;
    private static SqlTracker tracker;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        tracker = new SqlTracker(connection);
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenAddAndReplaceItem() {
        Item item = new Item("item_test");
        tracker.add(item);
        Assert.assertTrue(tracker.replace(item.getId(), new Item("replace_item")));
    }

    @Test
    public void whenDeleteItem() {
        Item item = new Item("delete_item");
        tracker.add(item);
        Assert.assertTrue(tracker.delete(item.getId()));
    }

    @Test
    public void whenFindALlItems() {
        Item item = new Item("Item");
        Item item2 = new Item("Item");
        tracker.add(item);
        tracker.add(item2);
        List<Item> expected = List.of(item, item2);
        assertThat(tracker.findAll(), is(expected));
    }

    @Test
    public void whenFindByNameItem() {
        Item item = new Item("test_name");
        tracker.add(item);
        List<Item> expected = List.of(item);
        assertThat(tracker.findByName("test_name"), is(expected));
    }

}