package com.derrick.park.assignment3_contacts.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ContactAddActivity extends SingleFragmentActivity {

  public static final String TAG = ContactAddActivity.class.getSimpleName();

  protected Fragment createFragment() {
    return new ContactAddFragment();
  }
}
