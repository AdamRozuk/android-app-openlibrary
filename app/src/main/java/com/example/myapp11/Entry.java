package com.example.myapp11;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 05.01.18.
 */
public class Entry {

    private String title;

    private String date;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
