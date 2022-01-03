package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ToDoListTest {
    Task t1;
    Task t2;
    Task t3;
    Task t4;

    ToDoList tl1;
    ToDoList tl2;
    ToDoList tl3;
    ToDoList tl4;
    ToDoList tl5;

    @BeforeEach
    public void runBefore() {
        t1 = new Task(1, "Project");
        t2 = new Task(2, "Lab");
        t3 = new Task(3, "Homework");
        t4 = new Task(4, "Quiz");

        tl1 = new ToDoList();
        tl1.addCreatedTask(t1);
        tl1.addCreatedTask(t2);

        tl2 = new ToDoList();
        t3.complete();
        tl2.addCreatedTask(t1);
        tl2.addCreatedTask(t3);

        tl3 = new ToDoList();

        tl4 = new ToDoList();
        tl4.addTask(1, "Project");

        tl5 = new ToDoList();
        tl5.addCreatedTask(t3);
        t4.complete();
        tl5.addCreatedTask(t4);

    }

    @Test
    public void testGetList() {
        tl1.getList();
        Task t = tl1.peekFirst();
        assertEquals(t1.taskName, t.taskName);
        assertEquals(t1.taskNum, t.taskNum);
    }

    @Test
    public void testAddTask() {
        tl3.addTask(1, "Project");
        Task t1 = tl4.getLast();
        Task t2 = tl3.getLast();

        assertEquals(t1.taskName, t2.taskName);
        assertEquals(t1.taskNum, t2.taskNum);

    }


    @Test
    public void testCompleteTask() {
        tl1.completeTask(1);
        assertEquals(1, tl1.seeTasksCompleted());
    }

    @Test
    public void testDeleteTask() {
        tl1.deleteTask(2);
        Task t0 = tl1.peekFirst();
        assertEquals(t1.taskNum, t0.taskNum);
        assertEquals(t1.taskName, t0.taskName);
    }

    @Test
    public void testDeleteNoTask() {
        tl1.deleteTask(0);
        Task t0 = tl1.peekFirst();
        assertEquals(t1.taskNum, t0.taskNum);
        assertEquals(t1.taskName, t0.taskName);
        Task t9 = tl1.peekLast();
        assertEquals(t2.taskNum, t9.taskNum);
        assertEquals(t2.taskName, t9.taskName);

    }

    @Test
    public void testSeeCompletedTasks() {
        assertEquals(0, tl1.seeTasksCompleted());
        assertEquals(2, tl5.seeTasksCompleted());
        assertEquals(1, tl2.seeTasksUncompleted());
    }

    @Test
    public void testSeeUncompletedTasks() {
        assertEquals(1, tl2.seeTasksUncompleted());
        assertEquals(2, tl1.seeTasksUncompleted());
    }

    @Test
    public void testLength() {
        int size = tl1.length();
        assertEquals(2,size);
    }

    @Test
    public void testGetTasks() {
        List<Task> tl = tl1.getTasks();
        assertEquals(tl1.peekFirst(),tl.get(0));
    }
}