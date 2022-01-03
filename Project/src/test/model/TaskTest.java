package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    void runBefore() {
        t1 = new Task(1, "Task");
        t2 = new Task(2, "Lab");
        t3 = new Task(3,"Project",true);
    }

    @Test
    void testConstructor() {
        assertEquals(1, t1.getTaskNum());
        assertEquals("Task", t1.getTaskName());
    }

    @Test
    void testBooleanConstructor() {
        assertEquals(3, t3.getTaskNum());
        assertEquals("Project", t3.getTaskName());
        assertEquals(true, t3.isCompleted);
    }

    @Test
    void testIsCompletedFalse() {
        assertEquals(false,t1.isCompleted());
    }

    @Test
    void testIsCompletedTrue() {
        t1.complete();
        assertEquals(true,t1.isCompleted);
    }

    @Test
    void testGetCompletionStatusFalse() {
        assertEquals("uncompleted",t1.getCompletionStatus());
    }

    @Test
    void testGetCompletionStatusTrue() {
        t1.complete();
        assertEquals("completed",t1.getCompletionStatus());
    }

    @Test
    void testUncomplete() {
        t2.complete();
        t2.uncomplete();
        assertEquals(false, t2.isCompleted);
    }
}
