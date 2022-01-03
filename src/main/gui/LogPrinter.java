package gui;

import model.EventLog;
import model.exceptions.LogException;


// Inspired by AlarmSystem repo
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
/**
 * EFFECTS: Defines behaviours that event log printers must support.
 */
public interface LogPrinter {
    /**
     * EFFECTS: Prints the log
     * @param el  the event log to be printed
     * @throws LogException when printing fails for any reason
     */
    void printLog(EventLog el) throws LogException;
}