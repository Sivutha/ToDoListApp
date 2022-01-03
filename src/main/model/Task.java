package model;

import org.json.JSONObject;
import persistence.Writable;

// Utilized Incident Class from lab5 as base for Task Class
// https://github.students.cs.ubc.ca/CPSC210-2021W-T1/lab5_p1q1r
// Represents a task having a task number, name, and completed/uncompleted status
public class Task implements Writable {
    protected int taskNum;
    protected String taskName;
    protected boolean isCompleted;

    // EFFECTS: Task is given a task number and name, and is not completed
    public Task(int taskNum, String taskName) {
        this.taskNum = taskNum;
        this.taskName = taskName;
        isCompleted = false;

    }

    // EFFECTS: Task is given a task number and name, and completion status
    public Task(int taskNum, String taskName,Boolean b) {
        this.taskNum = taskNum;
        this.taskName = taskName;
        this.isCompleted = b;

    }

    // EFFECTS: returns task number
    public int getTaskNum() {
        return taskNum;
    }

    // EFFECTS: returns task name
    public String getTaskName() {
        return taskName;
    }

    // EFFECTS: returns true if task is completed, false otherwise
    public boolean isCompleted() {
        return isCompleted;
    }

    // EFFECTS: returns "completed" if task is completed, "uncompleted" otherwise
    public String getCompletionStatus() {
        String completion = "";
        if (isCompleted == false) {
            completion = "uncompleted";
        } else {
            completion = "completed";
        }
        return completion;
    }

    // MODIFIES: this
    // EFFECTS: completes the task
    public void complete() {
        isCompleted = true;
    }

    // REQUIRES: isCompleted()
    // MODIFIES: this
    // EFFECTS: uncompletes the task
    public void uncomplete() {
        isCompleted = false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskNum", taskNum);
        json.put("taskName", taskName);
        json.put("isCompleted", isCompleted);
        return json;
    }

}
