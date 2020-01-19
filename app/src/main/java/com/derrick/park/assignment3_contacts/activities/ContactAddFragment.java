package com.derrick.park.assignment3_contacts.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.derrick.park.assignment3_contacts.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactAddFragment extends Fragment {

  private EditText mNameEditText;
  private EditText mCellEditText;
  private String mName;
  private String mCell;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_contact_add, container, false);

    mNameEditText = view.findViewById(R.id.edit_text_contact_name);
    mNameEditText.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            if (isValidName(s.toString())) {
              mName = s.toString();
            } else {
              mNameEditText.setError("first last");
            }
          }
        });

    mCellEditText = view.findViewById(R.id.edit_text_contact_phone);
    mCellEditText.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {}

          @Override
          public void afterTextChanged(Editable s) {
            if (isValidCell(s.toString())) {
              mCell = s.toString();
            } else {
              mCellEditText.setError("10 digits");
            }
          }
        });

    Button submitButton = view.findViewById(R.id.button_submit);
    submitButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Toast.makeText(getActivity().getApplicationContext(), "SUBMIT", Toast.LENGTH_SHORT)
                .show();
            Intent intent =
                ContactListActivity.newIntent(getActivity(), formatName(mName), formatCell(mCell));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
          }
        });

    getActivity().setTitle("Add contact");
    return view;
  }

  private boolean isValidName(String s) {
    String regex = "^[a-z]+ {1}[a-z]+";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(s);
    return matcher.matches();
  }

  private boolean isValidCell(String s) {
    String regex = "[0-9]{10}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(s);
    return matcher.matches();
  }

  private String formatName(String name) {
    String[] lower = name.toLowerCase().split(" ");
    String first = lower[0].substring(0, 1).toUpperCase() + lower[0].substring(1);
    String last = lower[1].substring(0, 1).toUpperCase() + lower[1].substring(1);
    return first + " " + last;
  }

  private String formatCell(String cell) {
    return cell.substring(0, 3) + "-" + cell.substring(3, 6) + "-" + cell.substring(6);
  }
}
