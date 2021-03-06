package com.derrick.park.assignment3_contacts.models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactList {
  @SerializedName("results")
  @Expose
  private ArrayList<Contact> contactList;

  public ArrayList<Contact> getContactList() {
    return contactList;
  }

  public void addContact(Contact contact) {
    contactList.add(contact);
  }
}
