package com.example.contactsv015;

import java.util.ArrayList;

/**
 * Created by Александр on 23.02.2017.
 */

public class GroupData {
    private String name;
    private ArrayList<String> contacts=new ArrayList<>();

    public GroupData(String name){
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "Name: "+name;
    }


    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

}
