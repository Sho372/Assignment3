package com.derrick.park.assignment3_contacts.activities;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ContactListActivity extends SingleFragmentActivity {

  public static final String TAG = ContactListActivity.class.getSimpleName();
  private Fragment mFragment;

  @Override
  protected Fragment createFragment() {
    mFragment = new ContactListFragment();
    return mFragment;
  }

  public static final String EXTRA_CONTACT_NAME =
      "com.derrick.park.assignment3_contacts.contact_name";
  public static final String EXTRA_CONTACT_CELL =
      "com.derrick.park.assignment3_contacts.contact_cell";

  public static Intent newIntent(Context packageContext, String name, String cell) {
    Intent intent = new Intent(packageContext, ContactListActivity.class);
    intent.putExtra(EXTRA_CONTACT_NAME, name);
    intent.putExtra(EXTRA_CONTACT_CELL, cell);
    return intent;
  }
}
