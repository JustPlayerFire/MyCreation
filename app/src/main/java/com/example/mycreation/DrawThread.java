package com.example.mycreation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.concurrent.TimeUnit;


public class DrawThread extends Thread {
    PetUI petUI;

    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    private Paint backgroundPaint = new Paint();
    Paint textPaint = new Paint();
    Pet pet;
    MainActivity activity;

    {
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(44);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder, Pet pet, PetUI petUI) {
        activity = (MainActivity) context;
        this.surfaceHolder = surfaceHolder;
        this.pet = pet;
        this.petUI = petUI;
    }
    public void requestStop() {
        running = false;
    }
    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null ) {
                try {
                    pet.x = ((int) canvas.getWidth() / 2) - petUI.image.getWidth() / 2;
                    pet.y = ((int) canvas.getHeight() / 2) - petUI.image.getHeight() / 2;

                    petUI.foodmarket_button_x = (float) (canvas.getWidth() / 3) - 135;
                    petUI.foodmarket_button_y = canvas.getHeight() - 135;

                    petUI.play_button_x = (float) ((canvas.getWidth() / 3) - 135) * 2;
                    petUI.play_button_y = canvas.getHeight() - 135;

                    petUI.stock_button_x = (float) ((canvas.getWidth() / 3) - 135) * 3;
                    petUI.stock_button_y = canvas.getHeight() - 135;

                    petUI.sleep_button_x = canvas.getWidth() - 135;
                    petUI.sleep_button_y = 300;

                    petUI.statics_button_x = canvas.getWidth() - 135;
                    petUI.statics_button_y = 500;

                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    canvas.drawBitmap(petUI.image, pet.x, pet.y, backgroundPaint);
                    canvas.drawText(pet.name,10, 50, textPaint);

                    canvas.drawText("Здоровье: " + pet.health, 10, 100, textPaint);
                    canvas.drawText("Сытость: " + pet.food, 10, 150, textPaint);
                    canvas.drawText("Счастье: " + pet.happiness, 10, 200, textPaint);
                    canvas.drawText("Монеты: " + pet.money, 10, 250, textPaint);
                    canvas.drawText("Энергия: " + pet.power, 10, 300, textPaint);
                    canvas.drawText(TimeUnit.MILLISECONDS.toMinutes(pet.time_finished - pet.time_started) + " мин.", 10, 350, textPaint);

                    canvas.drawBitmap(petUI.foodmarket_button, petUI.foodmarket_button_x, petUI.foodmarket_button_y, backgroundPaint);
                    canvas.drawBitmap(petUI.play_button, petUI.play_button_x, petUI.play_button_y, backgroundPaint);
                    canvas.drawBitmap(petUI.stock_button, petUI.stock_button_x, petUI.stock_button_y, backgroundPaint);
                    canvas.drawBitmap(petUI.sleep_button, petUI.sleep_button_x, petUI.sleep_button_y, backgroundPaint);
                    canvas.drawBitmap(petUI.statics_button_sprite, petUI.statics_button_x, petUI.statics_button_y, backgroundPaint);

                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            petUI.image = petUI.idle;
            if (pet.happiness <= 400 || pet.food <= 350){
                petUI.image = petUI.sad_sprite;
            }
            if (pet.power < 250){
                petUI.image = petUI.tired_sprite;
            }
            if (pet.health <= 500){
                petUI.image = petUI.dying_sprite;
            }
            if (pet.sleep == 1){
                petUI.sleep_button = petUI.sleep_button_sprite;
                petUI.image = petUI.sleep_sprite;
                backgroundPaint.setColor(Color.rgb(118, 66, 138));
                textPaint.setColor(Color.WHITE);
            }
            else {
                petUI.sleep_button = petUI.wakeup_button_sprite;
                backgroundPaint.setColor(Color.WHITE);
                textPaint.setColor(Color.BLACK);
            }
        }
    }
}