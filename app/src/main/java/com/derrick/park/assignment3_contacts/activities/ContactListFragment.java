package com.derrick.park.assignment3_contacts.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;
import com.derrick.park.assignment3_contacts.models.ContactList;
import com.derrick.park.assignment3_contacts.network.ContactClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListFragment extends Fragment {

  private RecyclerView mContactRecycleView;
  private ContactAdapter mAdapter;
  // Temporally database
  private static ArrayList<Contact> mContactList;

  public static final String TAG = ContactListFragment.class.getSimpleName();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate() called");
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    Call<ContactList> call = ContactClient.getContacts(10);

    call.enqueue(
        new Callback<ContactList>() {
          @Override
          public void onResponse(Call<ContactList> call, Response<ContactList> response) {
            if (response.isSuccessful()) {
              if (mContactList == null) {
                mContactList = response.body().getContactList();
              }
              for (Contact contact : mContactList) {
                Log.d(TAG, "onResponse: " + mContactList.size());
                Log.d(TAG, "onResponse: " + contact);
              }

              Intent intent = getActivity().getIntent();
              String name = intent.getStringExtra(ContactListActivity.EXTRA_CONTACT_NAME);
              String cell = intent.getStringExtra(ContactListActivity.EXTRA_CONTACT_CELL);

              if (name != null && cell != null) {
                String firstName = name.substring(0, name.indexOf(" "));
                String lastName = name.substring(name.indexOf(" ") + 1);
                Contact contact = new Contact(firstName, lastName, cell);
                mContactList.add(contact);
              }
              updateUI();
            }
          }

          @Override
          public void onFailure(Call<ContactList> call, Throwable t) {
            // Error Handling

          }
        });
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

    mContactRecycleView = view.findViewById(R.id.contact_recycler_view);
    mContactRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

    return view;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_contact_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.add_contact:
        // getActivity -> hosting activity as the context object
        Intent intent = new Intent(getActivity(), ContactAddActivity.class);
        startActivity(intent);
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void updateUI() {

    Collections.sort(
        mContactList,
        new Comparator<Contact>() {
          @Override
          public int compare(Contact c1, Contact c2) {
            return ("" + c1.getName()).compareTo("" + c2.getName());
          }
        });

    if (mAdapter == null) {
      mAdapter = new ContactAdapter(mContactList);
      mContactRecycleView.setAdapter(mAdapter);
    } else {
      mAdapter.notifyDataSetChanged();
    }
  }

  // ViewHolder
  private class ContactHolder extends RecyclerView.ViewHolder {

    private TextView mNameTextView;
    private TextView mCellTextView;
    private Contact mContact;

    public ContactHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
      super(inflater.inflate(R.layout.list_item_contact, parent, false));
      mNameTextView = itemView.findViewById(R.id.contact_name);
      mCellTextView = itemView.findViewById(R.id.contact_cell);
    }

    public void bind(Contact contact) {
      mContact = contact;
      String name = "" + mContact.getName();

      mNameTextView.setText(name);
      mCellTextView.setText(contact.getCell());
    }
  }

  // Adapter
  private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
      mContacts = contacts;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new ContactHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
      Contact contact = mContacts.get(position);
      holder.bind(contact);
    }

    @Override
    public int getItemCount() {
      return mContacts.size();
    }
  }
}
