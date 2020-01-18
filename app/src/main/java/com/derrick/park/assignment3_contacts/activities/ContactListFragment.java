package com.derrick.park.assignment3_contacts.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;
import com.derrick.park.assignment3_contacts.models.ContactList;
import com.derrick.park.assignment3_contacts.network.ContactClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListFragment extends Fragment {

  private RecyclerView mContactRecycleView;
  private ContactAdapter mAdapter;
  private ArrayList<Contact> mContactList;

  public static final String TAG = ContactListFragment.class.getSimpleName();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Call<ContactList> call = ContactClient.getContacts(10);

    call.enqueue(
        new Callback<ContactList>() {
          @Override
          public void onResponse(Call<ContactList> call, Response<ContactList> response) {
            if (response.isSuccessful()) {
              mContactList = response.body().getContactList();
              for (Contact contact : mContactList) {
                Log.d(TAG, "onResponse: " + mContactList.size());
                Log.d(TAG, "onResponse: " + contact);
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

  private void updateUI() {
    mAdapter = new ContactAdapter(mContactList);
    mContactRecycleView.setAdapter(mAdapter);
  }

  // ViewHolder
  private class ContactHolder extends RecyclerView.ViewHolder {

    private TextView mNameTextView;
    private TextView mPhoneNumberTextView;
    private Contact mContact;

    public ContactHolder(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
      super(inflater.inflate(R.layout.list_item_contact, parent, false));
      mNameTextView = itemView.findViewById(R.id.contact_name);
      mPhoneNumberTextView = itemView.findViewById(R.id.contact_phone_number);
    }

    public void bind(Contact contact) {
      mContact = contact;
      String name = "" + contact.getName();

      mNameTextView.setText(name);
      mPhoneNumberTextView.setText(contact.getCell());
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
