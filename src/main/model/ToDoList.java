package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;


import java.util.*;

// Represents a to-do list of tasks
public class ToDoList implements Writable {

    private LinkedList<Task> toDoList;

    // EFFECTS: Constructs an empty to-do list that takes Tasks
    public ToDoList() {
        toDoList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: Takes user input and creates a task which is then added to end of the to-do list
    public void addTask(int taskNum, String taskName) {

        Task t = new Task(taskNum, taskName);

        this.toDoList.add(t);

        EventLog.getInstance().logEvent(new Event("Added task: " + t.getTaskName()
                + " with task number: " + t.getTaskNum() + " to to-do list"));


    }

    // REQUIRES: non-empty list
    // EFFECTS: returns the to-do list
    public LinkedList<Task> getList() {
        return this.toDoList;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this to-do list
    public List<Task> getTasks() {
        return Collections.unmodifiableList(toDoList);
    }

    // REQUIRES: non-empty to-do list
    // MODIFIES: this
    // EFFECTS: Takes a taskNum and completes the task with matching taskNum and returns the to-do list
    public LinkedList<Task> completeTask(int taskNum) {
        for (Task t : this.toDoList) {
            if (t.taskNum == taskNum) {
                t.complete();
            }
        }
        return this.toDoList;
    }

    // REQUIRES: non-empty to-do list
    // MODIFIES: this
    // EFFECTS: takes a task number and removes task from to-do list with matching task number
    //          and returns the to-do list
    public LinkedList<Task> deleteTask(int taskNum) {
        for (Task t : this.toDoList) {
            if (t.taskNum == taskNum) {
                this.toDoList.remove(t);
                EventLog.getInstance().logEvent(new Event("Removed task: " + t.getTaskName()
                        + " with task number: " + t.getTaskNum() + " to to-do list"));
                return this.toDoList;

            }
        }
        return this.toDoList;
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: counts number of completed tasks and returns the total
    public int seeTasksCompleted() {
        int completed = 0;

        for (Task t : this.toDoList) {

            if (t.isCompleted == true) {
                completed++;
            }
        }
        return completed;
    }


    // REQUIRES: non-empty to-do list
    // EFFECTS: counts number of uncompleted tasks and returns the total
    public int seeTasksUncompleted() {
        int uncompleted = 0;

        for (Task t : this.toDoList) {

            if (t.isCompleted == false) {
                uncompleted++;
            }
        }
        return uncompleted;
    }


    // MODIFIES: this
    // EFFECTS: Adds a created task to the end of the to-do list
    public LinkedList<Task> addCreatedTask(Task task) {
        this.toDoList.add(task);
        EventLog.getInstance().logEvent(new Event("Added task: " + task.getTaskName()
                + " with task number: " + task.getTaskNum() + " to to-do list"));
        return this.toDoList;
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: returns last task
    public Task getLast() {
        return this.toDoList.getLast();
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: returns first task
    public Task peekFirst() {
        return this.toDoList.peekFirst();
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: returns last task
    public Task peekLast() {
        return this.toDoList.peekLast();
    }

    // EFFECTS: returns number of tasks in this to-do list
    public int length() {
        return toDoList.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("todo", thingiesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : toDoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }




}


