/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Mauricio Rios
 */
package ucf.assignments;
public class Item {
    //initialize boolean completion
    public Boolean completion;
    //initialize string description
    public String description;
    //initialize string due date
    public String dueDate;

    //declare constructor with completion, description, and due date
    public Item(Boolean completion, String description, String dueDate) {
        this.completion = completion;
        this.description = description;
        this.dueDate = dueDate;
    }

    //generate getters and setters

    public Boolean getCompletion() {
        return completion;
    }

    public void setCompletion(Boolean completion) {
        this.completion = completion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    //generate toString()
    @Override
    public String toString() {
        return "\nItem: \nCompletion: " + completion + "\nDescription: " + description + "\nDue Date: " + dueDate + "\n\n";
    }
}