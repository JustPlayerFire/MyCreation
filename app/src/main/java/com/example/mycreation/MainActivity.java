package com.example.mycreation;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
public class MainActivity extends Activity {

    DrawView drawView;

    boolean pet_is_dead = false;

    DBPet dbPet;

    Pet pet;
    HashMap<String, Integer> data = new HashMap<>();
    Intent service_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbPet = new DBPet(this);
        if (dbPet.selectAll().size() == 0 || !(dbPet.anyAlive())) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            final EditText name = new EditText(this);
            alertDialogBuilder.setView(name).setTitle("Введите имя питомца");
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dbPet.insert(name.getText().toString(), 1500, 1000, 750, 200, 1, 500, 0, 0, 0, 0, 0, System.currentTimeMillis(), System.currentTimeMillis());
                    initialization();
                }
            });
            alertDialogBuilder.create().show();
        }
        else{
            initialization();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        data.put("pet_health", drawView.pet.health);
        data.put("pet_happiness", drawView.pet.happiness);
        data.put("pet_food", drawView.pet.food);
        data.put("chicken", drawView.pet.count_chicken);
        data.put("cake", drawView.pet.count_cake);
        data.put("sandwich", drawView.pet.count_sandwich);
        data.put("pizza", drawView.pet.count_pizza);
        data.put("money", drawView.pet.money);
        update_pet();
    }

    @Override
    protected void onResume(){
        super.onResume();
        try{
            drawView.pet.health = data.get("pet_health");
            drawView.pet.happiness = data.get("pet_happiness");
            drawView.pet.food = data.get("pet_food");
            drawView.pet.money = data.get("money");
            drawView.pet.count_chicken = data.get("chicken");
            drawView.pet.count_cake = data.get("cake");
            drawView.pet.count_sandwich = data.get("sandwich");
            drawView.pet.count_pizza = data.get("pizza");
            update_pet();
        } catch (NullPointerException ex){}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(service_intent);
        update_pet();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unregisterReceiver(receiver);
        update_pet();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data_i) {
        super.onActivityResult(requestCode, resultCode, data_i);
        if (resultCode == 2) {
            drawView.pet.count_chicken += Integer.parseInt(data_i.getStringExtra("chicken"));
            drawView.pet.count_cake += Integer.parseInt(data_i.getStringExtra("cake"));
            drawView.pet.count_sandwich += Integer.parseInt(data_i.getStringExtra("sandwich"));
            drawView.pet.count_pizza += Integer.parseInt(data_i.getStringExtra("pizza"));
            drawView.pet.money = Integer.parseInt(data_i.getStringExtra("money"));

            data.put("chicken", data.get("chicken") + Integer.parseInt(data_i.getStringExtra("chicken")));
            data.put("cake", data.get("cake") + Integer.parseInt(data_i.getStringExtra("cake")));
            data.put("sandwich", data.get("sandwich") + Integer.parseInt(data_i.getStringExtra("sandwich")));
            data.put("pizza", data.get("pizza") + Integer.parseInt(data_i.getStringExtra("pizza")));
            data.put("money", Integer.parseInt(data_i.getStringExtra("money")));
        }
        else if (resultCode == 3){
            if (data.get("chicken") - Integer.parseInt(data_i.getStringExtra("chicken")) > 0){
                drawView.pet.health += 45 * (data.get("chicken") - Integer.parseInt(data_i.getStringExtra("chicken")));
                drawView.pet.happiness += 30 * (data.get("chicken") - Integer.parseInt(data_i.getStringExtra("chicken")));
                drawView.pet.food += 70 * (data.get("chicken") - Integer.parseInt(data_i.getStringExtra("chicken")));
            }
            if (data.get("cake") - Integer.parseInt(data_i.getStringExtra("cake")) > 0){
                drawView.pet.health += 30 * (data.get("cake") - Integer.parseInt(data_i.getStringExtra("cake")));
                drawView.pet.happiness += 150 * (data.get("cake") - Integer.parseInt(data_i.getStringExtra("cake")));
                drawView.pet.food += 50 * (data.get("cake") - Integer.parseInt(data_i.getStringExtra("cake")));
            }
            if (data.get("sandwich") - Integer.parseInt(data_i.getStringExtra("sandwich")) > 0){
                drawView.pet.health += 20 * (data.get("sandwich") - Integer.parseInt(data_i.getStringExtra("sandwich")));
                drawView.pet.happiness += 30 * (data.get("sandwich") - Integer.parseInt(data_i.getStringExtra("sandwich")));
                drawView.pet.food += 40 * (data.get("sandwich") - Integer.parseInt(data_i.getStringExtra("sandwich")));
            }
            if (data.get("pizza") - Integer.parseInt(data_i.getStringExtra("pizza")) > 0){
                drawView.pet.health += 150 * (data.get("pizza") - Integer.parseInt(data_i.getStringExtra("pizza")));
                drawView.pet.happiness += 100 * (data.get("pizza") - Integer.parseInt(data_i.getStringExtra("pizza")));
                drawView.pet.food += 100 * (data.get("pizza") - Integer.parseInt(data_i.getStringExtra("pizza")));
            }
            data.put("pet_health", drawView.pet.health);
            data.put("pet_happiness", drawView.pet.happiness);
            data.put("pet_food", drawView.pet.food);
            data.put("chicken", Integer.parseInt(data_i.getStringExtra("chicken")));
            data.put("cake", Integer.parseInt(data_i.getStringExtra("cake")));
            data.put("sandwich", Integer.parseInt(data_i.getStringExtra("sandwich")));
            data.put("pizza", Integer.parseInt(data_i.getStringExtra("pizza")));
        }
        else if (resultCode == 4){
            data.put("money", data.get("money") + Integer.parseInt(data_i.getStringExtra("money")));
            drawView.pet.money = data.get("money");
        }
        update_pet();
    }

    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            data.put("pet_food", drawView.pet.food -= Integer.parseInt(intent.getStringExtra("DECREASE")));
            if (pet.sleep == 1){
                data.put("pet_power", drawView.pet.power += Integer.parseInt(intent.getStringExtra("DECREASE")));
                if (data.get("pet_power") >= 500){
                    pet.sleep = 0;
                }
            }
            else{
                data.put("pet_power", drawView.pet.power -= Integer.parseInt(intent.getStringExtra("DECREASE")));
            }
            if (pet.power < 250 && pet.sleep == 0){
                data.put("pet_health", drawView.pet.health -= Integer.parseInt(intent.getStringExtra("DECREASE")) * 10);
                data.put("pet_happiness", drawView.pet.happiness -= Integer.parseInt(intent.getStringExtra("DECREASE")) * 10);
            }
            else{
                data.put("pet_health", drawView.pet.health -= Integer.parseInt(intent.getStringExtra("DECREASE")));
                data.put("pet_happiness", drawView.pet.happiness -= Integer.parseInt(intent.getStringExtra("DECREASE")));
            }

            if (data.get("pet_health") <= 0 ||
                    data.get("pet_happiness") <= 0 ||
                    data.get("pet_food") <= 0) {
                pet.setAlive(0);
                pet.setTime_finished(System.currentTimeMillis());
                update_pet();
                if (!pet_is_dead) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    final TextView text = new TextView(context);
                    text.setText("Ваш питомец умер.");
                    text.setPadding(35, 0, 0, 0);
                    alertDialogBuilder.setView(text).setTitle("Вы проиграли");
                    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            stopService(service_intent);
                            finish();
                        }
                    });
                    alertDialogBuilder.create().show();
                    pet_is_dead = true;
                }
            }
            update_pet();
        }
    };

    void update_pet(){
        pet.setHealth(drawView.pet.health);
        pet.setFood(drawView.pet.food);
        pet.setHappiness(drawView.pet.happiness);
        pet.setMoney(drawView.pet.money);
        pet.setPower(drawView.pet.power);
        pet.setCount_chicken(drawView.pet.count_chicken);
        pet.setCount_cake(drawView.pet.count_cake);
        pet.setCount_sandwich(drawView.pet.count_sandwich);
        pet.setCount_pizza(drawView.pet.count_pizza);
        pet.setTime_finished(System.currentTimeMillis());
        dbPet.update(pet);
    }

    void initialization(){
        ArrayList<Pet> pets = dbPet.selectAll();
        for (int i = 0; i < pets.size(); i++){
            if (pets.get(i).alive == 1){
                pet = pets.get(i);
                break;
            }
        }
        setContentView(drawView = new DrawView(this, pet));
        if (service_intent == null) {
            registerReceiver(receiver, new IntentFilter(PetLifecycleService.CHANNEL));
            service_intent = new Intent(this, PetLifecycleService.class);
        }
        data.put("chicken", drawView.pet.count_chicken);
        data.put("cake", drawView.pet.count_cake);
        data.put("sandwich", drawView.pet.count_sandwich);
        data.put("pizza", drawView.pet.count_pizza);
        data.put("money", drawView.pet.money);
        data.put("pet_health", drawView.pet.health);
        data.put("pet_happiness", drawView.pet.happiness);
        data.put("pet_food", drawView.pet.food);
        data.put("pet_power", drawView.pet.power);
        startService(service_intent);
    }
}