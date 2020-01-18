package com.derrick.park.assignment3_contacts.activities;

import androidx.fragment.app.Fragment;

public class ContactActivity extends SingleFragmentActivity {
  @Override
  protected Fragment createFragment() {
    return new ContactFragment();
  }
}
