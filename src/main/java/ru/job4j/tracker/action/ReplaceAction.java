package ru.job4j.tracker.action;

import ru.job4j.tracker.jdbc.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.output.Output;

public class ReplaceAction implements UserAction {

    private final Output output;

    public ReplaceAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Edit Item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.println("=== Edit item ===");
        int id = input.askInt("Enter id: ");
        String name = input.askStr("Enter new name for Item: ");
        Item item = new Item(name);
        if (tracker.replace(id, item)) {
            output.println("Edit item is done.");
        } else {
            output.println(String.format("Item with id=%s not found.", id));
        }
        return true;
    }
}
