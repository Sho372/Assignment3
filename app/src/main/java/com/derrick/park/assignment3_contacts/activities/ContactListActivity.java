package com.derrick.park.assignment3_contacts.activities;


import androidx.fragment.app.Fragment;

public class ContactListActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
    return new ContactListFragment();
  }
}
