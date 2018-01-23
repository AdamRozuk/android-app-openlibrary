package com.example.myapp11;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by adam on 05.01.18.
 */

public class Library extends AppCompatActivity {
    private FrameLayout fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tell the activity which XML layout is right
        setContentView(R.layout.lib_full_frag);

        // Enable the "Up" button for more navigation options
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fragmentContainer = (FrameLayout) findViewById(R.id.lib_full_frag_container);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lib_full_frag_container,ListFragment.newInstance(),"ListFragment")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){
        switch (menuitem.getItemId()){
            case R.id.menu_lib_back:
                back_menu();
                return true;
            default:
                return false;
        }
    }

    private  void back_menu(){

        this.finish();

    }
}
