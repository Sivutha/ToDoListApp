package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Class and Tests inspired by JsonReaderTest in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyToDoList.json");
        try {
            ToDoList tl = reader.read();
            assertEquals(0, tl.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralToDoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralToDoList.json");
        try {
            ToDoList tl = reader.read();
            Task t = tl.peekFirst();
            assertEquals("lab", t.getTaskName());
            List<Task> todo = tl.getTasks();
            assertEquals(2, todo.size());
            checkTask(1, "lab", false, todo.get(0));
            checkTask(2, "project", true, todo.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}