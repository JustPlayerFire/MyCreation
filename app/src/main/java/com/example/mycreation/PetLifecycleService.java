package com.example.mycreation;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;


public class PetLifecycleService extends Service {
    private static final int NOTIFICATION_ID = 101;
    private static final String NOTIFICATION_CHANNEL_ID = "my_channel_if";
    private static final String NOTIFICATION_CHANNEL_NAME = "My Channel Name";
    public static final String CHANNEL = "PETLIFE_SERVICE";
    Pet pet;
    DBPet dbPet;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        dbPet = new DBPet(this);
        ArrayList<Pet> pets = dbPet.selectAll();
        for (int i = 0; i < pets.size(); i++){
            if (pets.get(i).alive == 1){
                pet = pets.get(i);
                break;
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LifeStealerThread thread = new LifeStealerThread();
        thread.start();
        return START_STICKY;
    }

    private Notification createNotification(String text) {
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("MyCreation")
                .setContentText(text)
                .setSmallIcon(R.drawable.smile_mine_happy)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        return builder.build();
    }


    class LifeStealerThread extends Thread{

        int speed = 5;
        ArrayList<String> used_notifications = new ArrayList<>();

        @Override
        public void run(){
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Looper.prepare();
                new CountDownTimer(5000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        ArrayList<Pet> pets = dbPet.selectAll();
                        for (int i = 0; i < pets.size(); i++){
                            if (pets.get(i).alive == 1){
                                pet = pets.get(i);
                                break;
                            }
                        }
                    }

                    public void onFinish() {
                        Intent i = new Intent(CHANNEL);
                        i.putExtra("DECREASE", Integer.toString(speed));
                        sendBroadcast(i);
                        pet.health -= speed;
                        pet.food -= speed;
                        pet.happiness -= speed;
                        if (pet.sleep == 1){
                            pet.power += speed;
                            if (pet.power >= 500){
                                pet.sleep = 0;
                                notificationManager.notify(NOTIFICATION_ID, createNotification(pet.name + " уже бодрствует!"));
                                used_notifications.remove("sleep");
                            }
                        }
                        else{
                            pet.power -= speed;
                        }
                        if (pet.health <= 500){
                            if (!used_notifications.contains("dying")) {
                                notificationManager.notify(NOTIFICATION_ID, createNotification(pet.name + " на грани смерти..."));
                                used_notifications.add("dying");
                            }
                        }
                        else if (pet.alive == 0){
                            if (!used_notifications.contains("dead")) {
                                notificationManager.notify(NOTIFICATION_ID, createNotification("Ваш питомец умер..."));
                                used_notifications.add("dead");
                            }
                        }
                        else if (pet.food <= 350){
                            if (!used_notifications.contains("hungry")) {
                                notificationManager.notify(NOTIFICATION_ID, createNotification(pet.name + " хочет есть."));
                                used_notifications.add("hungry");
                            }
                        }
                        else if (pet.happiness <= 400){
                            if (!used_notifications.contains("sad")) {
                                notificationManager.notify(NOTIFICATION_ID, createNotification(pet.name + " грустит..."));
                                used_notifications.add("sad");
                            }
                        }
                        else if (pet.power <= 250){
                            if (!used_notifications.contains("sleep")) {
                                notificationManager.notify(NOTIFICATION_ID, createNotification(pet.name + " нуждается в отдыхе."));
                                used_notifications.add("sleep");
                            }
                        }
                        else {
                            used_notifications.remove("dying");
                            used_notifications.remove("dead");
                            used_notifications.remove("hungry");
                            used_notifications.remove("sad");
                            used_notifications.remove("sleep");
                        }
                        start();
                    }
                }.start();
                Looper.loop();
        }
    }
}

