package com.abraxy.abxtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TabHost;

public class InvActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inv);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Fighter Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Resources Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Tools Tab");

        tab1.setIndicator("Fighter");
        tab1.setContent(new Intent(this, InvActivityFighterTab.class));

        tab2.setIndicator("Resources");
        tab2.setContent(new Intent(this, InvActivityResourcesTab.class));

        tab3.setIndicator("Tools");
        tab3.setContent(new Intent(this, InvActivityToolsTab.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
    }
}
