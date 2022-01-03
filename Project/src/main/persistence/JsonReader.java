package persistence;


import model.Task;
import model.ToDoList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//Inspired by JsonReader in JsonSerializationDemo
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads to-do list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList tl = new ToDoList();
        addTasks(tl, jsonObject);
        return tl;
    }

    // MODIFIES: tl
    // EFFECTS: parses tasks from JSON object and adds them to a to-do list
    private void addTasks(ToDoList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("todo");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tl, nextTask);

        }
    }

    // MODIFIES: tl
    // EFFECTS: parses thingy from JSON object and adds it to a to-do list
    private void addTask(ToDoList tl, JSONObject jsonObject) {
        int num = jsonObject.getInt("taskNum");
        String name = jsonObject.getString("taskName");
        Boolean b = jsonObject.getBoolean("isCompleted");
        Task task = new Task(num, name, b);
        tl.addCreatedTask(task);
    }
}

