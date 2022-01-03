package gui;

import model.Task;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Listener utilized by the AddTask Action
// Inspired by HireListener in ListDemo
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
class AddListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private JButton button;

    public AddListener(JButton button) {
        this.button = button;
    }

    //Required by ActionListener.
    //EFFECTS: takes input in AddTask panel text fields and adds new task to the to-do list
    public void actionPerformed(ActionEvent e) {

        int taskNum = ToDoListGUI.getNumTextField();
        String taskName = ToDoListGUI.getNameTextField();
        Task t = new Task(taskNum,taskName);


        ToDoListGUI.getToDo().addCreatedTask(t);
        ToDoListGUI.getAddPanel().setVisible(false);
        ToDoListGUI.getDesktop().remove(ToDoListGUI.getAddPanel());
        ToDoListGUI.getToDoList().setVisible(false);
        ToDoListGUI.getDesktop().remove(ToDoListGUI.getToDoList());

        ToDoListGUI.updateGUI();



    }



    //Required by DocumentListener.
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    //Required by DocumentListener.
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    //Required by DocumentListener.
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // EFFECTS: Enables "Enter" button on add task panel if it is not enabled
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    //EFFECTS: Disables button if the text fields are empty
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}