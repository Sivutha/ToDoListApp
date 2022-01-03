package gui;


import model.Task;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Listener utilized by the RemoveTask Action
// Inspired by FireListener in ListDemo
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
class RemoveListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private JButton button;

    public RemoveListener(JButton button) {
        this.button = button;
    }

    //Required by ActionListener.
    //EFFECTS: takes input in AddTask panel text fields and adds new task to the to-do list
    public void actionPerformed(ActionEvent e) {

        int taskNum = ToDoListGUI.getNumTextField();

        ToDoListGUI.getListModel().addElement(taskNum);


        ToDoListGUI.getToDo().deleteTask(taskNum);
        ToDoListGUI.getRemovePanel().setVisible(false);
        ToDoListGUI.getDesktop().remove(ToDoListGUI.getRemovePanel());
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

    // EFFECTS: Enables "Enter" button on remove task panel if it is not enabled
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    //EFFECTS: Disables button if the text field is empty
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
