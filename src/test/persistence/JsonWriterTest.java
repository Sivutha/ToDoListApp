package persistence;

import model.Task;
import model.ToDoList;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Inspired by JsonWriterTest in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList tl = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ToDoList tl = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyToDoList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyToDoList.json");
            tl = reader.read();
            assertEquals(0, tl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ToDoList tl = new ToDoList();
            tl.addCreatedTask(new Task(3, "CPSC 210 Assignment", true));
            tl.addCreatedTask(new Task(4, "grocery shopping", false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralToDoList.json");
            tl = reader.read();
            Task t = tl.peekFirst();
            assertEquals("CPSC 210 Assignment", t.getTaskName());
            List<Task> todo = tl.getTasks();
            assertEquals(2, todo.size());
            checkTask(3, "CPSC 210 Assignment", true, todo.get(0));
            checkTask(4, "grocery shopping", false, todo.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
