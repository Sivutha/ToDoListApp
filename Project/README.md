# To-Do List

## Who, What, Why

This application will allow people who want to better organized to create a to-do list to keep 
track of tasks that need to be completed.

This project is of interest to me because I am a person who has trouble keeping track of work I 
need to compete. Being able to create a to-do list in order to help organize my thoughts 
and plan my actions to finish all my work would be very beneficial to my 
productivity.



***User Stories***
- *As a user, I want to be able to add a task to my to-do list*
- *As a user, I want to be able to view the list of tasks on my to-do list*
- *As a user, I want to be able to mark a task as complete on my to-do list*
- *As a user, I want to be able to delete a task from my to-do-list*
- *As a user, I want to be able to see the number of incomplete and number of completed tasks on my to-do list*

*Data Persistence User Stories*

-  *As a user, I want to be able to save my to-do list to file*
-  *As a user, I want to be able to be able to load my to-do list from file* 

***Phase 4: Task 2***

- Added task: lab with task number: 1 to to-do list
- Removed task: lab with task number: 1 to to-do list
- Added task: lab with task number: 1 to to-do list
- Added task: project with task number: 5 to to-do list
- Added task: project with task number: 400 to to-do list
- Saved current to-do list

***Phase 4: Task 3***

Reflecting on my UML diagram for my project, there is not much coupling between 
my classes to refactor and improve, however there are other places where the 
design could be refactored and improved.

The first refactor that could be done is in my ToDoList class where 
the seeTasksCompleted() and seeTasksUncompleted() methods  are very similar and 
could be extracted to reduce duplication and improve cohesion, if I were to ever 
change the way the methods behaved.

In the ToDoListGUI class, a refactor could be made for some portions of the code that
creates the AddTask and RemoveTask panels as much of the code is the same, similarly 
reducing duplication. 

The greatest refactoring that could be done would be to split up the ToDoListGUI 
class into more classes as it currently holds the methods to handle every aspect of 
the GUI. If I had more time to work on the project, I would create separate classes to 
hand different aspects of the GUI. Specifically, I would extract the methods handling 
the creation and functionality of the AddPanel, RemovePanel, and ToDoList display 
into separate classes leaving the ToDoListGUI just creating the base panel and 
control panel, along with their functionality. In doing so, my program's adherence to 
the Single Responsibility Principle would be improved.
