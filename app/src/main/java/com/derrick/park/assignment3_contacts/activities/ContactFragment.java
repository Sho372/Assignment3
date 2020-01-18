package com.derrick.park.assignment3_contacts.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.derrick.park.assignment3_contacts.R;
import com.derrick.park.assignment3_contacts.models.Contact;
import com.derrick.park.assignment3_contacts.models.ContactList;
import com.derrick.park.assignment3_contacts.network.ContactClient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFragment extends Fragment {

  private ArrayList<Contact> mContactList;
  public static final String TAG = ContactFragment.class.getSimpleName();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_fragment);
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
    View v = inflater.inflate(R.layout.fragment_contact, container, false);
    return v;
  }
}
