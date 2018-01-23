package com.example.myapp11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 21.01.18.
 */

class Baza extends Entry{
    private static final Baza ourInstance = new Baza();
    public List<Entry> data = new ArrayList<>();

    static Baza getInstance() {
        return ourInstance;
    }

    private Baza() {
    }

    public void deleteBase(Entry entry){ data.remove(entry);}

    public void setBase(Entry entry){
        data.add(entry);
    }

    public List<Entry> getBase(){
        return data;
    }

    public int getLenght(){
        return data.size();
    }


}
