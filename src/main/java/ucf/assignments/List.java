/*
 *  UCF COP3330 Summer 2021 Assignment 4 Solution
 *  Copyright 2021 Mauricio Rios
 */
package ucf.assignments;
import java.util.ArrayList;

public class List {
    //initialize String listName
    public String listName;
    //initialize ArrayList<Item>
    public ArrayList<Item> itemList;

    //declare constructor with ArrayList<Item>
    public List(String listName, ArrayList<Item> itemList) {
        this.listName = listName;
        this.itemList = itemList;
    }

    //generate getters and setter for Item

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<Item> getItem() {
        return itemList;
    }

    public void setItem(ArrayList<Item> item) {
        this.itemList = item;
    }

    //generate toString()

    @Override
    public String toString() {
        return listName+ " : " + itemList;
    }
}