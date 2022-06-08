package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.jdbc.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

import java.util.List;

public class DeleteALotOfData implements UserAction {

    private final Output out;

    public DeleteALotOfData(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Delete a lot of items ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        List<Item> item = tracker.findByName("test");
        for (Item value : item) {
            tracker.delete(value.getId());
        }
        out.println("A lot of items is successfully deleted!");
        return true;
    }
}
