package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Inspired by JsonTest in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    //EFFECTS: Compares all the values of a task with inputted values and returns true if they are the same
    protected void checkTask(int taskNum, String taskName, boolean b, Task task) {
        assertEquals(taskNum, task.getTaskNum());
        assertEquals(taskName, task.getTaskName());
        assertEquals(b, task.isCompleted());
    }
}