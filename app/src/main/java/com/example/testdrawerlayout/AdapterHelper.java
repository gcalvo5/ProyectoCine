package com.example.testdrawerlayout;

import java.util.ArrayList;

public class AdapterHelper {
    public void update(CustomAdapter arrayAdapter, ArrayList<DataModel> listOfObject){
        arrayAdapter.clear();
        for (DataModel object : listOfObject){
            arrayAdapter.add(object);
        }
    }
    public void update(ComentariosCustomAdapter arrayAdapter, ArrayList<ComentariosDataModel> listOfObject){
        arrayAdapter.clear();
        for (ComentariosDataModel object : listOfObject){
            arrayAdapter.add(object);
        }
    }
}
