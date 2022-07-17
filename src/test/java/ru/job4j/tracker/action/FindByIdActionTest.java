package ru.job4j.tracker.action;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;
import ru.job4j.tracker.store.MemTracker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindByIdActionTest {

    @Test
    public void whenFindByIdAction() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        tracker.add(new Item("Find by id item"));
        FindByIdAction findByIdAction = new FindByIdAction(out);

        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("0");
        findByIdAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(out.toString(), is(tracker.findAll().get(0) + ln));
        assertThat(tracker.findAll().get(0).getName(), is("Find by id item"));
    }

}