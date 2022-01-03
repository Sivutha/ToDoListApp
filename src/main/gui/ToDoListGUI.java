package gui;


import model.ToDoList;
import model.Task;

import persistence.JsonReader;
import persistence.JsonWriter;

import model.EventLog;
import model.Event;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowListener;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.*;


// GUI for ToDoList Application
// Some methods inspired by AlarmController UI in AlarmSystem repository
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
public class ToDoListGUI extends JFrame implements LogPrinter {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 800;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todolist.json";

    private static ToDoList todo;
    private static JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private static JInternalFrame toDoList;
    private static JTextField numTextField;
    private static JTextField nameTextField;
    private static JInternalFrame addPanel;
    private static JInternalFrame removePanel;
    private static JList list;
    private static DefaultListModel listModel;
    private static DefaultListModel listModelAdd;
    private BufferedImage image;
    private JInternalFrame loadImage;


    // Inspired by AlarmController UI
    // MODIFIES: this
    // EFFECTS: Constructor sets up to-do list GUi
    public ToDoListGUI() throws FileNotFoundException {
        todo = new ToDoList();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        controlPanel = new JInternalFrame("Control Panel", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());
        toDoList = new JInternalFrame("To-Do List");
        listModel = new DefaultListModel();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        setContentPane(desktop);
        setTitle("To-Do List");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();
        addList(listModel);
        setupToDoListAndControlPanel();

        WindowListener wl = (new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ToDoListGUI.this.windowClosing();
            }
        });
        addWindowListener(wl);

        centreOnScreen();
        setVisible(true);
    }

    // EFFECTS: Sets up and adds the control panel and to-do list panel to main frame
    public void setupToDoListAndControlPanel() {
        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);
        toDoList.pack();
        toDoList.setVisible(true);
        desktop.add(toDoList);

    }

    // EFFECTS: Prints event log to console and exits the program
    public void windowClosing() {
        printLog(EventLog.getInstance());
        System.exit(0);
    }

    // Inspired by AlarmController UI
    // EFFECTS:  Helper to add control buttons.
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(new JButton(new AddTaskAction()));
        buttonPanel.add(new JButton(new RemoveTaskAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new LoadAction()));


        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: Adds the to-do list panel to the GUI
    private static void addList(DefaultListModel listModel) {
        list = new JList(listModel);
        list.setVisibleRowCount(20);
        list.setFixedCellHeight(20);
        list.setFixedCellWidth(100);

        toDoList.add(list);
        toDoList.setLocation(0, 140);

    }

    public static DefaultListModel getListModel() {
        return listModel;
    }

    public static JList getList() {
        return list;
    }

    public static JInternalFrame getToDoList() {
        return toDoList;
    }

    // Inspired by AlarmController UI
    // EFFECTS:  Represents action to be taken when user wants to add a new task
    //           to the system.
    private class AddTaskAction extends AbstractAction {

        AddTaskAction() {
            super("Add Task");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            addPanel = new JInternalFrame("Add Task", false);
            JLabel numLabel = new JLabel("Enter task number");
            numTextField = new JTextField(10);
            JLabel nameLabel = new JLabel("Enter task name");
            nameTextField = new JTextField(10);
            JButton button = new JButton("Enter");
            AddListener addListener = new AddListener(button);
            button.setActionCommand("Enter");
            button.addActionListener(addListener);
            addPanel.setLayout(new GridLayout(4, 1));
            addPanel.add(numLabel);
            addPanel.add(numTextField);
            addPanel.add(nameLabel);
            addPanel.add(nameTextField);
            addPanel.add(button);

            addPanel.pack();
            addPanel.setLocation(150, 0);
            addPanel.setVisible(true);
            desktop.add(addPanel);


        }

    }

    // EFFECTS: Takes text in numTextField and returns it as an integer
    public static int getNumTextField() {
        return Integer.valueOf(numTextField.getText());
    }

    // EFFECTS: Takes text in nameTextField and returns it as a String
    public static String getNameTextField() {
        return String.valueOf(nameTextField.getText());
    }


    public static ToDoList getToDo() {
        return todo;
    }


    public static JDesktopPane getDesktop() {
        return desktop;
    }


    public static JInternalFrame getAddPanel() {
        return addPanel;
    }


    // Inspired by AlarmController UI
    // EFFECTS:  Represents the action to be taken when the user wants to remove
    //           a task from the to-do list
    private class RemoveTaskAction extends AbstractAction {

        RemoveTaskAction() {
            super("Remove Task");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            removePanel = new JInternalFrame("Remove Task", false);
            JLabel numLabel = new JLabel("Enter task number");
            numTextField = new JTextField(10);
            JButton button = new JButton("Enter");
            RemoveListener removeListener = new RemoveListener(button);
            button.setActionCommand("Enter");
            button.addActionListener(removeListener);
            removePanel.setLayout(new GridLayout(4, 1));
            removePanel.add(numLabel);
            removePanel.add(numTextField);
            removePanel.add(button);

            removePanel.pack();
            removePanel.setLocation(400, 0);
            removePanel.setVisible(true);
            desktop.add(removePanel);

        }
    }

    public static JInternalFrame getRemovePanel() {
        return removePanel;
    }

    // Inspired by AlarmController UI
    // EFFECTS:  Represents action to be taken when user wants to save
    //           the to-do list
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save To-Do List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(todo);
                jsonWriter.close();
                System.out.println("Saved to-do list to " + JSON_STORE);
                EventLog.getInstance().logEvent(new Event("Saved current to-do list"));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }

        }
    }


    // Inspired by AlarmController UI
    // EFFECTS:  Represents action to be taken when user wants to load the saved
    //           to-do list
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load To-Do List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                todo = jsonReader.read();
                System.out.println("Loaded to-do list from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }


            toDoList.setVisible(false);
            desktop.remove(toDoList);
            updateGUI();

            loadImage();




        }
    }

    // EFFECTS: loads a welcome back image
    // Photo retrieved from: http://minimiracleschildcare.com/enrollment-form/mini-miracles-blog/welcome-back/
    private void loadImage() {
        try {
            image = ImageIO.read(new File("./src/main/gui/images/WelcomeBack.png"));
        } catch (IOException e) {
            System.out.println("No Image");
        }
        loadImage = new JInternalFrame("Welcome Back!", false, true);
        loadImage.setLayout(new FlowLayout());
        loadImage.add(new JLabel(new ImageIcon(image)));
        loadImage.pack();
        loadImage.setVisible(true);
        loadImage.setLocation(130, 175);
        desktop.add(loadImage);


    }


    // EFFECTS: Returns the to-do list showing task number, task name
    private void viewList() {
        for (Task t : todo.getList()) {
            System.out.println(t.getTaskNum() + ". " + t.getTaskName());
        }
    }

    // Inspired by AlarmController UI
    // EFFECTS:  Represents action to be taken when user clicks desktop
    //          to switch focus. (Needed for key handling.)

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            ToDoListGUI.this.requestFocusInWindow();
        }
    }

    // Inspired by AlarmController UI
    //EFFECTS: Helper to centre main application window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // EFFECTS: Updates the to-do list panel of the GUI
    public static void updateGUI() {
        listModel.clear();
        for (Task t : todo.getList()) {
            listModel.addElement(t.getTaskNum() + ". " + t.getTaskName());
        }
        System.out.println(listModel);
        addList(listModel);
        toDoList.pack();
        toDoList.setVisible(true);
        desktop.add(toDoList);

    }

    @Override
    public void printLog(EventLog el) {
        for (model.Event e : el) {
            System.out.println(e.getDescription().toString());

        }
    }


}

