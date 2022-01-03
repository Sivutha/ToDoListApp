package ui;


import java.util.*;
import java.io.*;


import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;


// To-do List Application
// Some methods inspired by Bank Teller App and JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class ToDoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private ToDoList todo;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs and runs the to-do list application
    public ToDoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        todo = new ToDoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDoList();
    }

    // Utilizes runTellerApp as base
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runToDoList() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            displayMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // Utilized processCommand() in TellerApp as base
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addTask();
        } else if (command.equals("v")) {
            viewList();
        } else if (command.equals("c")) {
            completeTask();
        } else if (command.equals("d")) {
            deleteTask();
        } else if (command.equals("n")) {
            viewCompletedList();
            countCompleted();
            countUncompleted();
        } else if (command.equals("s")) {
            saveToDoList();
        } else if (command.equals("l")) {
            loadToDoList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // Utilized init() in TellerApp as base
    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        todo = new ToDoList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // Utilized displayMenu() in TellerApp as base
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add task");
        System.out.println("\tv -> view to-do list");
        System.out.println("\tc -> complete task");
        System.out.println("\td -> delete task");
        System.out.println("\tn -> show number of completed/uncompleted tasks");
        System.out.println("\ts -> save to-do list to file");
        System.out.println("\tl -> load to-do list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: takes user input about task info and creates a task which is added to the to-do list
    private void addTask() {
        System.out.println("Enter a task number: ");
        int taskNum = input.nextInt();

        System.out.println("Enter a task name: ");

        input.nextLine();

        String taskName = input.nextLine();
        todo.addTask(taskNum, taskName);

        viewList();
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: Returns the to-do list showing task number, task name
    private void viewList() {
        for (Task t : todo.getList()) {
            System.out.println(t.getTaskNum() + ". " + t.getTaskName());
        }
    }

    // REQUIRES: non-empty to-do list
    // MODIFIES: this
    // EFFECTS: takes a task number and completes task with matching number from to-do list and returns the
    // to-do list showing completion
    private void completeTask() {
        System.out.println("Enter number of task to be completed:");
        int taskNum = input.nextInt();

        todo.completeTask(taskNum);

        viewCompletedList();
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: Returns the to-do list showing task number, task name, and completion status for each element
    private void viewCompletedList() {
        for (Task t : todo.getList()) {
            System.out.println(t.getTaskNum() + ". " + t.getTaskName() + " (" + t.getCompletionStatus() + ")");
        }
    }

    // REQUIRES: non-empty to-do list
    // MODIFIES: this
    // EFFECTS: takes a task number and removes task with matching number from to-do list and returns the to-do list
    private void deleteTask() {
        System.out.println("Enter number of task to be deleted:");
        int taskNum = input.nextInt();

        todo.deleteTask(taskNum);

        viewList();
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: counts the the number of completed tasks and returns it
    public void countCompleted() {
        System.out.println("Number of completed tasks: " + todo.seeTasksCompleted());
    }

    // REQUIRES: non-empty to-do list
    // EFFECTS: counts the the number of uncompleted tasks and returns it
    public void countUncompleted() {
        System.out.println("Number of uncompleted tasks: " + todo.seeTasksUncompleted());
    }

    // EFFECTS: saves the to-do list to file
    private void saveToDoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(todo);
            jsonWriter.close();
            System.out.println("Saved to-do list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads to-do list from file
    private void loadToDoList() {
        try {
            todo = jsonReader.read();
            System.out.println("Loaded to-do list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}





