package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.jdbc.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

public class CreateALotOfData implements UserAction {

    private final Output out;

    public CreateALotOfData(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "=== Create a lot of items ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        for (int i = 0; i < 10_000_000; i++) {
            Item item = new Item("test");
            tracker.add(item);
        }
        out.println("A lot of items successfully added!");
        return true;
    }
}
