package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ToDoControllerTest {

    @Test
    void addListAssertTrue() {
        ToDoController tdc = new ToDoController();
        // create arraylist of Items to add to our expected list
        ArrayList<Item> homeworkItemList = new ArrayList<>();
        // create a List and give it the name "List Title" and the created arraylist of items
        List expected = new List("Homework List", homeworkItemList);
        // Use addList("List Title") and return a list
        List output = tdc.addList("Homework List");
        // create boolean to false
        boolean equals = expected.listName.equals(output.listName) && expected.itemList.equals(output.itemList);
        // assert true for boolean
        assertTrue(equals);
    }

    @Test
    void editListAssertTrue() {
        ToDoController tdc = new ToDoController();
        // create arraylist of Items to add to our expected list
        ArrayList<Item> defaultItemList = new ArrayList<>();
        // create our list that we will change with the method
        List inputList = new List("Old list name", defaultItemList);
        // create our expected list and use same defaultItemList because we won't be adding items in this method
        List expectedList = new List("Edited List", defaultItemList);
        // add to our global list
        tdc.ToDoLists.add(inputList);
        // use method and give it our list and index of 0 since we only have list
        List outputList = tdc.editList("Edited List", 0);
        // create boolean and if expected and output lists are equals make boolean true
        boolean equals = expectedList.listName.equals(outputList.listName);
        // assert true for boolean
        assertTrue(equals);
    }

    @Test
    void editListAssertFalseUsingTheOldName() {
        ToDoController tdc = new ToDoController();
        // create arraylist of Items to add to our expected list
        ArrayList<Item> defaultItemList = new ArrayList<>();
        // create our list that we will change with the method
        List inputList = new List("Old list name", defaultItemList);
        // create our expected list and use same defaultItemList because we won't be adding items in this method
        List expectedList = new List("Old list name", defaultItemList);
        // add to our global list
        tdc.ToDoLists.add(inputList);
        // use method and give it our list and index of 0 since we only have list
        List outputList = tdc.editList("Edited List", 0);
        // create boolean and if expected and output lists are equals make boolean true
        boolean equals = expectedList.listName.equals(outputList.listName);
        // assert true for boolean
        assertFalse(equals);
    }

    @Test
    void addItemAssertTrue() {
        ToDoController tdc = new ToDoController();

        // add values to our global list
        ArrayList<Item> defaultItemList = new ArrayList<>();
        List list1 = new List("First List", defaultItemList);
        tdc.ToDoLists.add(list1);

        // create our expected list
        ArrayList<Item> itemList = new ArrayList<>();
        // create list
        List expectedList = new List("Expected", itemList);
        // create an Item and assign it values
        Item item = new Item(false, "Homework", "2021-07-18");
        // add item to our expected list
        expectedList.itemList.add(item);

        // use addItem() and give it the same values for our
        List outputList = tdc.addItem("Homework", "2021-07-18",0);

        // create boolean and set to true if the values within expect and output are equal
        boolean equals = outputList.itemList.get(0).completion.equals(expectedList.itemList.get(0).completion) &&
                outputList.itemList.get(0).description.equals(expectedList.itemList.get(0).description) &&
                outputList.itemList.get(0).dueDate.equals(expectedList.itemList.get(0).dueDate);
        // assert true for boolean
        assertTrue(equals);
    }

    @Test
    void viewListsAssertTrue() {
        ToDoController tdc = new ToDoController();

        // add values to our global list
        ArrayList<Item> itemList1 = new ArrayList<>();
        List list1 = new List("First List", itemList1);
        tdc.ToDoLists.add(list1);

        // give this list an item
        ArrayList<Item> itemList2 = new ArrayList<>();
        List list2 = new List("Second List", itemList2);
        Item item = new Item(false, "Homework", "2021-07-18");
        list2.itemList.add(item);
        tdc.ToDoLists.add(list2);

        // create our expected list
        ArrayList<Item> expectedItemList = new ArrayList<>();
        List expectedList = new List("Expected", expectedItemList);
        Item expectedItem = new Item(false,"Homework", "2021-07-18");
        expectedList.itemList.add(expectedItem);

        // use method to return a specific list to view
        List outputList = tdc.viewLists(1);

        // create boolean and compare the list selected (outputList) to our expectedList
        boolean equals = outputList.itemList.get(0).completion.equals(expectedList.itemList.get(0).completion) &&
                outputList.itemList.get(0).description.equals(expectedList.itemList.get(0).description) &&
                outputList.itemList.get(0).dueDate.equals(expectedList.itemList.get(0).dueDate);
        // assert true for boolean
        assertTrue(equals);
    }

    @Test
    void viewListsAssertFalseUsingWrongIndex() {
        ToDoController tdc = new ToDoController();

        // add values to our global list
        ArrayList<Item> itemList1 = new ArrayList<>();
        List list1 = new List("First List", itemList1);
        Item item0 = new Item(true,"Walk the dog", "2021-08-19");
        list1.itemList.add(item0);
        tdc.ToDoLists.add(list1);

        // give this list an item
        ArrayList<Item> itemList2 = new ArrayList<>();
        List list2 = new List("Second List", itemList2);
        Item item = new Item(false, "Homework", "2021-07-18");
        list2.itemList.add(item);
        tdc.ToDoLists.add(list2);

        // create our expected list
        ArrayList<Item> expectedItemList = new ArrayList<>();
        List expectedList = new List("Expected", expectedItemList);
        Item expectedItem = new Item(false,"Homework", "2021-07-18");
        expectedList.itemList.add(expectedItem);

        // use method to return a specific list to view
        List outputList = tdc.viewLists(0);

        // create boolean and compare the list selected (outputList) to our expectedList
        boolean equals = outputList.itemList.get(0).completion.equals(expectedList.itemList.get(0).completion) &&
                outputList.itemList.get(0).description.equals(expectedList.itemList.get(0).description) &&
                outputList.itemList.get(0).dueDate.equals(expectedList.itemList.get(0).dueDate);
        // assert true for boolean
        assertFalse(equals);
    }

    @Test
    void deleteListAssertTrue() {
        ToDoController tdc = new ToDoController();

        // add values to our global list
        ArrayList<Item> itemList1 = new ArrayList<>();
        List list1 = new List("First List", itemList1);
        tdc.ToDoLists.add(list1);

        // add a second list
        ArrayList<Item> itemList2 = new ArrayList<>();
        List list2 = new List("Second List", itemList2);
        tdc.ToDoLists.add(list2);

        // add a third list
        ArrayList<Item> itemList3 = new ArrayList<>();
        List list3 = new List("Third List", itemList3);
        tdc.ToDoLists.add(list3);

        // create our expected version of global list
        ArrayList<List> expectedList = new ArrayList<>();

        ArrayList<Item> expectedItemList1 = new ArrayList<>();
        List expectedList1 = new List("First List", expectedItemList1);
        expectedList.add(expectedList1);

        ArrayList<Item> expectedItemList2 = new ArrayList<>();
        List expectedList2 = new List("Third List", expectedItemList2);
        expectedList.add(expectedList2);

        // delete the second list
        tdc.deleteList(1);

        // create boolean to false
        boolean equals = false;

        // create counter for the loop
        int count = 0;
        // compare expectedList to global list
        for(int i=0; i<tdc.ToDoLists.size(); i++) {
            if(tdc.ToDoLists.get(i).listName.equals(expectedList.get(i).listName)) {
                count++;
            }
            if(count == tdc.ToDoLists.size()) {
                equals = true;
            }
        }
        // assert boolean true
        assertTrue(equals);
    }

    @Test
    void deleteListAssertFalseByComparingToFullLists() {
        ToDoController tdc = new ToDoController();

        // add values to our global list
        ArrayList<Item> itemList1 = new ArrayList<>();
        List list1 = new List("First List", itemList1);
        tdc.ToDoLists.add(list1);

        // add a second list
        ArrayList<Item> itemList2 = new ArrayList<>();
        List list2 = new List("Second List", itemList2);
        tdc.ToDoLists.add(list2);

        // add a third list
        ArrayList<Item> itemList3 = new ArrayList<>();
        List list3 = new List("Third List", itemList3);
        tdc.ToDoLists.add(list3);

        // create our expected version of global list
        ArrayList<List> expectedList = new ArrayList<>();

        ArrayList<Item> expectedItemList1 = new ArrayList<>();
        List expectedList1 = new List("First List", expectedItemList1);
        expectedList.add(expectedList1);

        ArrayList<Item> expectedItemList2 = new ArrayList<>();
        List expectedItem2 = new List("Second List", expectedItemList2);
        expectedList.add(expectedItem2);

        ArrayList<Item> expectedItemList3 = new ArrayList<>();
        List expectedList3 = new List("Third List", expectedItemList3);
        expectedList.add(expectedList3);

        // delete the second list
        tdc.deleteList(1);

        // create boolean to false
        boolean equals = false;

        // create counter for the loop
        int count = 0;
        // compare expectedList to global list
        for(int i=0; i<tdc.ToDoLists.size(); i++) {
            if(tdc.ToDoLists.get(i).listName.equals(expectedList.get(i).listName)) {
                count++;
            }
            if(count == tdc.ToDoLists.size()) {
                equals = true;
            }
        }
        // assert boolean true
        assertFalse(equals);
    }


    @Test
    void saveAll() {
        ToDoController tdc = new ToDoController();

        // Give the list items
        ArrayList<Item> itemList = new ArrayList<>();
        List list = new List("First list", itemList);
        Item item = new Item(false, "Homework", "2021-07-18");
        list.itemList.add(item);
        Item item2 = new Item(true,"Walk the dog","2021-09-10");
        list.itemList.add(item2);
        Item item3 = new Item(true,"Shower","2021-08-31");
        list.itemList.add(item3);
        tdc.ToDoLists.add(list);
        // expected string to compare when file is saved successfully
        String expected = "File Saved!";
        // HAVE TO CHANGE PATHS FOR YOUR PC. write path and method returns message
        String output = tdc.saveAll("/Users/mauriciorios/Desktop/SaveFileTest.txt");
        // compare expected to output message
        assertEquals(expected, output);
    }

    @Test
    void loadAllSuccessfulLoad() {
        ToDoController tdc = new ToDoController();
        // expected string when file is loaded correctly
        String expected = "File Loaded!";
        // put path and when file loaded correctly the file returns a message
        String output = tdc.loadAll("/Users/mauriciorios/Desktop/SaveFileTest.txt");
        // compare expected and output
        assertEquals(expected, output);
    }

    @Test
    void loadAllFailedLoad() {
        ToDoController tdc = new ToDoController();
        // expected string when file is loaded correctly
        String expected = "File not loaded";
        // put path of wrong file and when file loaded incorrectly the file returns a message
        String output = tdc.loadAll("/Users/mauriciorios/Desktop/SaveTest.txt");
        // compare expected and output
        assertEquals(expected, output);
    }

    @Test
    void deleteItem() {
        ToDoController tdc = new ToDoController();

        // fill our global list with items
        ArrayList<Item> itemList = new ArrayList<>();
        List list = new List("List one", itemList);
        Item item1 = new Item(false, "Homework", "2021-07-18");
        Item item2 = new Item(false, "Go running", "2021-08-20");
        Item item3 = new Item(true, "Eat", "2021-06-01");

        list.itemList.add(item1);
        list.itemList.add(item2);
        list.itemList.add(item3);
        tdc.ToDoLists.add(list);

        // create our expected list and fill it
        ArrayList<Item> expectedItemList = new ArrayList<>();
        List expectedList = new List("List one", expectedItemList);
        Item expectedItem1 = new Item(false, "Go running", "2021-08-20");
        Item expectedItem2 = new Item(true, "Eat", "2021-06-01");

        expectedList.itemList.add(expectedItem1);
        expectedList.itemList.add(expectedItem2);

        List outputList = tdc.deleteItem(0, 0);

        // create boolean to false
        boolean equals = false;
        // create counter for the loop
        int counter = 0;
        // compare the output and expected list and set boolean to true if equal
        for(int i=0; i<outputList.itemList.size(); i++) {
            // if the item at index i in our outputList is equal to our expectedList we up the counter
            if(outputList.itemList.get(i).completion.equals(expectedList.itemList.get(i).completion) &&
                    outputList.itemList.get(i).description.equals(expectedList.itemList.get(i).description) &&
                    outputList.itemList.get(i).dueDate.equals(expectedList.itemList.get(i).dueDate)) {
                counter++;
            }
            if(counter == outputList.itemList.size()) {
                equals = true;
            }
        }
        // assert boolean true
        assertTrue(equals);
    }

    @Test
    void editItem() {
        ToDoController tdc = new ToDoController();

        // give this list an item
        ArrayList<Item> itemList = new ArrayList<>();
        List list = new List("First list", itemList);
        Item item = new Item(false, "Homework", "2021-07-18");
        list.itemList.add(item);
        tdc.ToDoLists.add(list);

        // create expected list and fill
        ArrayList<Item> outputItemList = new ArrayList<>();
        List expectedList = new List("Output List", outputItemList);
        Item expectedItem = new Item(false,"New Description", "2021-01-01");
        expectedList.itemList.add(expectedItem);

        List outputList = tdc.editItem("New Description", "2021-01-01", 0,0);

        // create boolean and set to true if the contents of expectedList and outputList are equal
        boolean equals = outputList.itemList.get(0).completion.equals(expectedList.itemList.get(0).completion) &&
                outputList.itemList.get(0).description.equals(expectedList.itemList.get(0).description) &&
                outputList.itemList.get(0).dueDate.equals(expectedList.itemList.get(0).dueDate);
        // assert true for boolean
        assertTrue(equals);
    }

    @Test
    void changeCompletion() {
        ToDoController tdc = new ToDoController();

        // give this list an item
        ArrayList<Item> itemList = new ArrayList<>();
        List list = new List("First list", itemList);
        Item item = new Item(false, "Homework", "2021-07-18");
        list.itemList.add(item);
        Item item2 = new Item(true,"Walk the dog","2021-09-10");
        list.itemList.add(item2);
        tdc.ToDoLists.add(list);

        // create our expected list with item changed
        ArrayList<Item> expectedItemList = new ArrayList<>();
        List expected = new List("expected", expectedItemList);
        Item item3 = new Item(false,"Walk the dog", "2021-09-10");
        expected.itemList.add(item3);

        // send index for our list and the item we want to change
        List output = tdc.changeCompletion(0,1);

        // create boolean and compare the expected item boolean to the output
        boolean equals = output.itemList.get(1).completion.equals(expected.itemList.get(0).completion);

        // assert true for boolean
        assertTrue(equals);
    }

    @Test
    void deleteItemsAssertEqualsOfStrings() {
        ToDoController tdc = new ToDoController();

        // Give the list items
        ArrayList<Item> itemList = new ArrayList<>();
        List list = new List("First list", itemList);
        Item item = new Item(false, "Homework", "2021-07-18");
        list.itemList.add(item);
        Item item2 = new Item(true,"Walk the dog","2021-09-10");
        list.itemList.add(item2);
        Item item3 = new Item(true,"Shower","2021-08-31");
        list.itemList.add(item3);
        tdc.ToDoLists.add(list);

        // create expected list that will be empty
        ArrayList<Item> emptyList = new ArrayList<>();
        List expectedList = new List("Empty", emptyList);

        // give index for the list to be cleared and method will return an empty list
        List outputList = tdc.deleteItems(0);

        // turn to string the contents of both outputList and expectedList
        String expectedString = expectedList.itemList.toString();
        String outputString =  outputList.itemList.toString();

        // assert true for boolean
        assertEquals(expectedString, outputString);
    }
}