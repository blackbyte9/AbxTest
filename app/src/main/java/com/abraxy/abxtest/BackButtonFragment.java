package com.abraxy.abxtest;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackButtonFragment extends Fragment implements View.OnClickListener {


    public BackButtonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("BackButtonFragment", "onCreateView");
        Button myButton = new Button(getContext());
        myButton.setText(R.string.backButton_text);
        myButton.setOnClickListener(this);
        myButton.setBackgroundColor(Color.RED);
        return myButton;
    }

    @Override
    public void onClick(View v) {
        Log.v("BackButtonFragment", "onClick");
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivity(intent);
    }
}
