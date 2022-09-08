package ru.job4j.tracker.jdbc;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HbmTrackerTest {


    @Test
    @Ignore
    public void whenFindALlItems() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("Item");
            Item item2 = new Item("Item");
            tracker.add(item);
            tracker.add(item2);
            List<Item> expected = List.of(item, item2);
            assertThat(tracker.findAll(), Is.is(expected));
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item();
            item.setName("test1");
            tracker.add(item);
            Item result = tracker.findById(item.getId());
            assertThat(result.getName(), is(item.getName()));
        }
    }

    @Test
    public void whenReplaceNameOfItem() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("test");
            tracker.add(item);
            Item replaceItems = new Item("Update replace");
            tracker.replace(item.getId(), replaceItems);
            Item result = tracker.findById(item.getId());
            assertThat(result.getName(), is(item.getName()));
        }
    }

    @Test
    public void whenDeleteItem() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item("delete_item");
            tracker.add(item);
            tracker.delete(item.getId());
            Assert.assertNull(tracker.findById(item.getId()));
        }
    }

    @Test
    @Ignore
    public void whenFindByNameItem() {
        try (var tracker = new HbmTracker()) {
            Item item = tracker.add(new Item("test_name"));
            Item item1 = tracker.add(new Item("test_name"));
            Item item2 = tracker.add(new Item("test_name"));
            Item item3 = tracker.add(new Item("java"));
            Item item4 = tracker.add(new Item("spring"));
            List<Item> expected = List.of(
                    item, item1, item2
            );
            assertThat(tracker.findByName("test_name"), Is.is(expected));
        }
    }

}