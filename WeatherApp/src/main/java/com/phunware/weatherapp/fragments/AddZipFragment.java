package com.phunware.weatherapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phunware.weatherapp.R;


public class AddZipFragment extends Fragment
{
    private EditText _editText;
    private Button _addButton;

    private AddFragmentListener _listener;

    public AddZipFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_dialog, container);
        _editText = (EditText) view.findViewById(R.id.zip_code);
        _addButton = (Button)view.findViewById(R.id.add_button);

        _addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdd();
            }
        });

        return view;
    }

    public interface AddFragmentListener {
        public void onAddZipCode(String zip);
        public void onAddCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AddFragmentListener) {
            _listener = (AddFragmentListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }


    private void onAdd()
    {
        String zip = _editText.getText().toString();

        if (zip.isEmpty() || zip.length() != 5)
        {
            Toast.makeText(getActivity(), "Invalid zip code", Toast.LENGTH_LONG).show();
            return;
        }

        // hide the soft keyboard
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(_editText.getWindowToken(), 0);

        _editText.setText("");

        _listener.onAddZipCode(zip);
    }

}
