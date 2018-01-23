package com.example.myapp11;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.inverce.mod.core.IM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 05.01.18.
 */

public class ListFragment extends Fragment {
    private RecyclerView recycler;
    private ListAdapter adapter;


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 13. unpack the coverID from its trip inside your Intent
        //String bookTitle = this.getIntent().getExtras().getString("bookTitle");
        //String authorName = this.getIntent().getExtras().getString("authorName");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.mylibrary,parent,false);
        recycler = (RecyclerView) rootView.findViewById(R.id.fragment_list);
        recycler.setLayoutManager(new LinearLayoutManager(IM.context()));

        adapter = new ListAdapter(Baza.getInstance().getBase());
        recycler.setAdapter(adapter);

        Log.d("addtolibr2", Baza.getInstance().getBase().toString());

        return rootView;
    }

    public void addTaskToList(Entry entry) {
        adapter.addTask(entry);
    }

    public void removeSelectedTasks() {
        adapter.removeSelected();
    }


}
