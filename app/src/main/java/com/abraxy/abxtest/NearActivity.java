package com.abraxy.abxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NearActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("NearActivity", "onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_near);

        mListView = (ListView) findViewById(R.id.nearListView);

        updateList();
    }


    protected List<String> getList() {
        List<String> ret = new ArrayList<>();
        ret.add("Erster Eintrag");
        ret.add("Zweiter Eintrag");
        ret.add("Dritter Eintrag");
        ret.add("Vierter Eintrag");
        ret.add("Fünfter Eintrag");
        ret.add("Erster Eintrag");
        ret.add("Zweiter Eintrag");
        ret.add("Dritter Eintrag");
        ret.add("Vierter Eintrag");
        ret.add("Fünfter Eintrag");
        ret.add("Erster Eintrag");
        ret.add("Zweiter Eintrag");
        ret.add("Dritter Eintrag");
        ret.add("Vierter Eintrag");
        ret.add("Fünfter Eintrag");

        return ret;
    }

    protected void updateList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getList());
        mListView.setAdapter(adapter);
    }
}
