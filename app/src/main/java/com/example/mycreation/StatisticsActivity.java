package com.example.mycreation;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StatisticsActivity extends ListActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    DBPet dbPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbPet = new DBPet(this);
        ArrayList<Pet> pets = dbPet.selectAll();
        for (int i = 0; i < pets.size(); i++){
            if (pets.get(i).alive == 0){
                arrayList.add(pets.get(i).name + " - " + (TimeUnit.MILLISECONDS.toMinutes(pets.get(i).time_finished - pets.get(i).time_started) + " мин."));
            }
        }
        String[] myArr = new String[arrayList.size()];
        arrayList.toArray(myArr);
        ArrayAdapter<String> namesAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArr);
        setListAdapter(namesAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
