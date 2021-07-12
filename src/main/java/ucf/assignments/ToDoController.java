/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Mauricio Rios
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ToDoController implements Initializable {

    // initialize global ArrayList<List> ToDoLists to store all lists
    public ArrayList<List> ToDoLists = new ArrayList<>();
    // initialize observable list for showing in tableView
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        completeCellDisplay.setCellValueFactory(new PropertyValueFactory<>("completion"));
        descriptionCellDisplay.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateCellDisplay.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        itemDisplay.setItems(observableList);
    }

    ObservableList<Item> observableList = FXCollections.observableArrayList();

    @FXML
    public ListView<String> toDoListDisplay;
    @FXML
    public TableView<Item> itemDisplay;
    @FXML
    public TableColumn<Item, String> descriptionCellDisplay;
    @FXML
    public TableColumn<Item, String> dueDateCellDisplay;
    @FXML
    public TableColumn<Item, Boolean> completeCellDisplay;
    @FXML
    public TextField EnterListNameDisplay;
    @FXML
    public TextField EditListNameDisplay;
    @FXML
    public TextField dueDateDisplay;
    @FXML
    public TextArea descriptionDisplay;
    @FXML
    public TextArea editDescriptionDisplay;
    @FXML
    public TextField editDueDateDisplay;
    @FXML
    public TextField pathInputDisplay;
    @FXML
    public CheckBox completedItems;
    @FXML
    public CheckBox uncompletedItems;


    @FXML
    public void addListButtonClicked(ActionEvent actionEvent) {
        // get string in EnterListNameDisplay
        String titleString = EnterListNameDisplay.getText();
        // if statement to prevent creating a list with no name
        if(titleString.length()>0) {
            // send it in the method and method returns a list
            List newList = addList(titleString);
            // we add it to the main Arraylist (ToDoList)
            ToDoLists.add(newList);
            // clear display
            EnterListNameDisplay.clear();
            // add to toDoListDisplay
            toDoListDisplay.getItems().add(newList.getListName());
        }
    }

    @FXML
    public void editListButtonClicked(ActionEvent actionEvent) {
        // get string in EditListNameDisplay
        String newEditTitle = EditListNameDisplay.getText();
        // if statement to prevent changing the name to nothing
        if(newEditTitle.length()>0) {
            // get index for selected list in ListDisplay
            int index = toDoListDisplay.getSelectionModel().getSelectedIndex();
            // pull list out to send to method
            List oldList = ToDoLists.get(index);
            // send string and index to method
            List editedList = editList(newEditTitle, index);
            // method returns list with edited name and add to toDoListDisplay
            // remove the list that we are going to change
            ToDoLists.remove(index);
            // add the edited list to the Arraylist
            ToDoLists.add(index, editedList);
            // clear display
            EditListNameDisplay.clear();
            // remove list from List Display
            toDoListDisplay.getItems().remove(index);
            // add new name to the list display in the same index
            toDoListDisplay.getItems().add(index,newEditTitle);
        }
    }

    @FXML
    public void addItemButtonClicked(ActionEvent actionEvent) {
        // get string that is in description display
        String description = descriptionDisplay.getText();
        // get string that is in due date display
        String dueDate = dueDateDisplay.getText();
        // get index from display to know which list we add to
        int index = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // if statement to prevent creating a item without inputs and that a list has been selected to add to
        if(description.length()>0 && dueDate.length()>0 && index >-1) {
            // send both strings and index to method
            List updatedList = addItem(description, dueDate, index);
            // method returns our list with the added item, we add that to our item display
            addToObservableList(updatedList);
            // clears both input displays
            descriptionDisplay.clear();
            dueDateDisplay.clear();
        }
    }

    @FXML
    public void searchItemsButtonClicked(ActionEvent actionEvent) {
        // get index of the list we are viewing
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // create new list that will receive from method
        List searchedList;
        // if statement to cover all options for searching
        // if both checkboxes are selected we just get the whole list
        if(completedItems.isSelected() && uncompletedItems.isSelected()) {
            searchedList = ToDoLists.get(listIndex);
        // if completed checkbox selected we send the listIndex and true to method
        } else if( completedItems.isSelected()) {
             searchedList= searchItems(listIndex, true);
        //if uncompleted checkbox selected we send the listIndex and false to method
        } else {
            searchedList= searchItems(listIndex, false);
        }
        // we get back the list of true, false, or both and put to observable list
        addToObservableList(searchedList);
        // put our checkboxes back to unchecked
        completedItems.setIndeterminate(false);
        uncompletedItems.setIndeterminate(false);
    }


    @FXML
    public void viewSelectedListButtonClicked(ActionEvent actionEvent) {
        // get index of selected list
        int index = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // send index to method
        List itemList = viewLists(index);
        // method returns the List of items to display in itemDisplay
        addToObservableList(itemList);
    }

    @FXML
    public void deleteListButtonClicked(ActionEvent actionEvent) {
        // get index of selected list
        int index = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // send index to method
        deleteList(index);
        // update on toDoListDisplay
        toDoListDisplay.getItems().remove(index);
    }


    @FXML
    public void saveAllListsButtonClicked(ActionEvent actionEvent) throws IOException {
        // get path input from display
        String path = pathInputDisplay.getText();
        // send path to method to save to txt file
        String message = saveAll(path);
        // message to show file was saved successfully
        System.out.println(message);

    }

    @FXML
    public void loadAllListsButtonClicked(ActionEvent actionEvent) {
        String path = pathInputDisplay.getText();
        String message = loadAll(path);
        for (List toDoList : ToDoLists) {
            toDoListDisplay.getItems().add(toDoList.listName);
        }
        // message to show file was loaded successfully
        System.out.println(message);

    }

    @FXML
    public void deleteItemButtonClicked(ActionEvent actionEvent) {
        // get index of selected item in itemDisplay
        int itemIndex = itemDisplay.getSelectionModel().getSelectedIndex();
        // get index for List that is being shown
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // send both indexes to method to delete the specific item
        List updatedItemList = deleteItem(itemIndex, listIndex);
        // method returns list with deleted item
        // update to ToDoLists
        addToObservableList(updatedItemList);
    }

    @FXML
    public void editItemClicked(ActionEvent actionEvent) {
        // get string from editDescriptionDisplay
        String editDescription = editDescriptionDisplay.getText();
        // get string for editDueDate
        String editDueDate = editDueDateDisplay.getText();
        // get index selected in itemDisplay
        int itemIndex = itemDisplay.getSelectionModel().getSelectedIndex();
        // get index for selected list
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // send the strings and indexes to method to edit the selected item
        List editedList = editItem(editDescription, editDueDate, itemIndex, listIndex);
        // method returns list with the updated item
        // update the tableview with the return list
        addToObservableList(editedList);
    }

    @FXML
    public void itemClicked(MouseEvent mouseEvent) {
        /* purpose for this method is to show the description and dueDate in a textField/textArea to be viewed
         since the tableview is too small */
        // get index for the list that is being viewed
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // get index for the item being selected
        int itemIndex = itemDisplay.getSelectionModel().getSelectedIndex();
        // display the description of selected item in textarea
        editDescriptionDisplay.setText(ToDoLists.get(listIndex).itemList.get(itemIndex).description);
        // display the dueDate of selected item in textarea
        editDueDateDisplay.setText(ToDoLists.get(listIndex).itemList.get(itemIndex).dueDate);
    }

    @FXML
    public void toDoListClicked(MouseEvent mouseEvent) {
        // get selected index in lists display
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // display selected list name in EditListNameDisplay
        EditListNameDisplay.setText(ToDoLists.get(listIndex).listName);
    }

    @FXML
    public void completeButtonClicked(ActionEvent actionEvent) {
        // get index of selected item in tableview
        int itemIndex = itemDisplay.getSelectionModel().getSelectedIndex();
        // get index of selected list in ToDoList
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // send the two indexes to the method that will change that item to true/false
        List editList = changeCompletion(listIndex, itemIndex);
        // method returns the updated list and we show in tableview
        addToObservableList(editList);
    }

    @FXML
    public void deleteAllItemsClicked(ActionEvent actionEvent) {
        // get the index of selected list in ToDoListDisplay
        int listIndex = toDoListDisplay.getSelectionModel().getSelectedIndex();
        // send the index to method to be deleted and return the empty list
        List updateList = deleteItems(listIndex);
        // show the empty list in tableView
        addToObservableList(updateList);

    }

    public List addList(String titleName) {
        // declare a new ArrayList<Item>
        ArrayList<Item> newItemList = new ArrayList<>();
        // assign titleName for string and newItemList for new List and return it
        return new List(titleName,newItemList);
    }

    public List editList(String newEditTitle, int index) {
        // get list that belongs at the index
        List editList = ToDoLists.get(index);
        // replace the listName with the newEditTitle
        editList.setListName(newEditTitle);
        // return the edited list
        return editList;
    }

    public List addItem(String description, String dueDate, int index) {
        // get list that is at the index
        List addToList = ToDoLists.get(index);
        // use the description and dueDate strings to create a new item and set false for completion
        Item item = new Item(false, description, dueDate);
        // add Item to list
        addToList.itemList.add(item);
        // return the list with the added item
        return addToList;
    }

    public List searchItems(int listIndex, boolean search) {
        // create a new list of Items to add the only true or false
        ArrayList<Item> newItemList = new ArrayList<>();
        // new List so we can show on tableview
        List newList = new List("searchList",newItemList);
        // use listIndex to know what list we want
        List searchThisList = ToDoLists.get(listIndex);
        // for loop to compare the search boolean to the items inside of the list
        for (int i=0; i<searchThisList.itemList.size(); i++) {
            // if the item at i equals search boolean then we add it to our new list
            if (searchThisList.itemList.get(i).completion == search) {
                Item item = searchThisList.itemList.get(i);
                newList.itemList.add(item);
            // if the item at index i does not equal the search boolean we put a empty item at that index
            } else {
                Item otherItem = new Item(null," "," ");
                newList.itemList.add(otherItem);
            }
        }
            // return the new list of searched items
            return newList;
    }

    public List viewLists(int index) {
        // receives index number and returns the list that it belongs to
        return ToDoLists.get(index);
    }

    public void deleteList(int index) {
        // receives index number and uses it to delete the list at that spot
        ToDoLists.remove(index);
    }


    public String saveAll(String path) {
        String message = "File not saved";
        // initialize fileWriter
        FileWriter fileWriter = null;
        try {
            // put path for fileWriter
            fileWriter = new FileWriter(path);
            // loop through the whole ToDoList and print listName with the name after
            for (List toDoList : ToDoLists) {
                fileWriter.write("listName\n");
                fileWriter.write(toDoList.listName + "\n");
                // loop through the itemList for this list and print item and then the contents of the Item
                for (int j = 0; j < toDoList.itemList.size(); j++) {
                    fileWriter.write("item\n");
                    fileWriter.write(toDoList.itemList.get(j).completion + "\n" +
                            toDoList.itemList.get(j).description + "\n" +
                            toDoList.itemList.get(j).dueDate + "\n");
                }
                message = "File Saved!";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fileWriter != null;
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return string to know it saved
        return message;
    }

    public String loadAll(String path) {
        String message = "File not loaded";
        try {
            // counter to know how many lists we have made so far
            int counter = -1;
            // declare line to load bufferedReader to
            String line;
            // initialize bufferReader
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            // keep looping until bufferReader doesn't receive any more strings from the txt file
            while((line = bufferedReader.readLine()) != null) {
                // if the line equals listName then we know the next line is the name of the List. We create a new list
                if(line.equals("listName")) {
                    counter++;
                    ArrayList<Item> newList = new ArrayList<>();
                    line = bufferedReader.readLine();
                    List list = new List(line, newList);
                    ToDoLists.add(list);
                    // if the next line is item then we know its the content of an item coming in the strings and we
                    // create a new list
                } else if(line.equals("item")) {
                    line = bufferedReader.readLine();
                    String completion = line;
                    boolean complete = Boolean.parseBoolean(completion);
                    line = bufferedReader.readLine();
                    String description = line;
                    line = bufferedReader.readLine();
                    String dueDate = line;
                    Item item = new Item(complete,description,dueDate);
                    ToDoLists.get(counter).itemList.add(item);
                    message = "File Loaded!";
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        // return string to know it loaded
        return message;
    }

    public List deleteItem(int itemIndex, int listIndex) {
        // retrieve selected/clicked list from list display
        List selectedList = ToDoLists.get(listIndex);
        // delete item from the list with itemIndex
        selectedList.itemList.remove(itemIndex);
        // return list
        return selectedList;
    }

    public List editItem(String editDescription, String editDueDate, int itemIndex, int listIndex) {
        // get the list that the item belongs to with listIndex
        List editList = ToDoLists.get(listIndex);
        // get the specific item from the list with itemIndex
        Item editItem = editList.itemList.get(itemIndex);
        // set the edited description to the item selected
        editItem.setDescription(editDescription);
        // set the edited dueDate to the item selected
        editItem.setDueDate(editDueDate);
        // return the list with the edited item
        return editList;
    }

    public void addToObservableList(List displayList) {
        // clear the tableView for a new list
        observableList.clear();
        // for loop to add all the items with the given list
        for(int i=0; i<displayList.itemList.size(); i++) {
            observableList.add(i,displayList.getItem().get(i));
        }
    }

    public List changeCompletion(int listIndex, int itemIndex) {
        // use listIndex to get our list
        List list = ToDoLists.get(listIndex);
        // if the item at itemIndex is false we change to true
        // if the item at itemIndex is true we change to false
        list.itemList.get(itemIndex).completion = !list.itemList.get(itemIndex).completion;
        // return our updated list
        return list;
    }

    public List deleteItems(int listIndex) {
        // use listIndex to get our list
        List deleteItems = ToDoLists.get(listIndex);
        // delete all items from the list
        deleteItems.itemList.clear();
        // return updated list
        return deleteItems;
    }
}