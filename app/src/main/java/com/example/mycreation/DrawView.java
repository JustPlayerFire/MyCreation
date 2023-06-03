package com.example.mycreation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    DrawThread drawThread;
    MainActivity activity;

    Pet pet;
    PetUI petUI = new PetUI(getContext());

    public DrawView(Context context, Pet pet) {
        super(context);
        getHolder().addCallback(this);
        activity = new MainActivity();
        this.pet = pet;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder(), pet, petUI);
        drawThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (((int)event.getX() >= drawThread.petUI.foodmarket_button_x && (int)event.getX() <= drawThread.petUI.foodmarket_button_x + 135) &&
                ((int)event.getY() >= drawThread.petUI.foodmarket_button_y && (int)event.getY() <= drawThread.petUI.foodmarket_button_y + 135) ){
            Intent i = new Intent(getContext(), FoodMarketActivity.class);
            i.putExtra("money", ((MainActivity)getContext()).data.get("money") + "");
            ((Activity)getContext()).startActivityForResult(i, 1);
        }
        else if (((int)event.getX() >= drawThread.petUI.play_button_x && (int)event.getX() <= drawThread.petUI.play_button_x + 135) &&
                ((int)event.getY() >= drawThread.petUI.play_button_y && (int)event.getY() <= drawThread.petUI.play_button_y + 135) ){
            if (pet.sleep == 1){
                Toast.makeText((Activity)getContext(), "Вы не можете играть, пока ваш питомец спит!", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent i = new Intent(getContext(), FlappySmileActivity.class);
                i.putExtra("money", ((MainActivity) getContext()).data.get("money") + "");
                ((Activity) getContext()).startActivityForResult(i, 1);
            }
        }
        else if (((int)event.getX() >= drawThread.petUI.stock_button_x && (int)event.getX() <= drawThread.petUI.stock_button_x + 135) &&
                ((int)event.getY() >= drawThread.petUI.stock_button_y && (int)event.getY() <= drawThread.petUI.stock_button_y + 135) ){
            Intent i = new Intent(getContext(), FoodStockActivity.class);
            i.putExtra("chicken", ((MainActivity)getContext()).data.get("chicken") + "");
            i.putExtra("cake", ((MainActivity)getContext()).data.get("cake") + "");
            i.putExtra("sandwich", ((MainActivity)getContext()).data.get("sandwich") + "");
            i.putExtra("pizza", ((MainActivity)getContext()).data.get("pizza") + "");

            ((Activity)getContext()).startActivityForResult(i, 1);

        }
        else if (((int)event.getX() >= drawThread.petUI.sleep_button_x && (int)event.getX() <= drawThread.petUI.sleep_button_x + 135) &&
                ((int)event.getY() >= drawThread.petUI.sleep_button_y && (int)event.getY() <= drawThread.petUI.sleep_button_y + 135) ){
            if (pet.sleep == 0){
                pet.sleep = 1;
            }
            else{
                pet.sleep = 0;
            }
        }
        else if (((int)event.getX() >= drawThread.petUI.statics_button_x && (int)event.getX() <= drawThread.petUI.statics_button_x + 135) &&
                ((int)event.getY() >= drawThread.petUI.statics_button_y && (int)event.getY() <= drawThread.petUI.statics_button_y + 135)){
            Intent i = new Intent(getContext(), StatisticsActivity.class);
            ((Activity)getContext()).startActivity(i);
        }
        return false;
    }
}