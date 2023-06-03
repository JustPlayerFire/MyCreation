package com.example.mycreation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PetUI {
    Bitmap foodmarket_button;
    Bitmap play_button;
    Bitmap stock_button;
    Bitmap sleep_button;
    Bitmap sleep_button_sprite;
    Bitmap sleep_sprite;
    Bitmap wakeup_button_sprite;
    Bitmap statics_button_sprite;
    Bitmap tired_sprite;
    Bitmap dying_sprite;
    Bitmap image;
    Bitmap idle;
    Bitmap sad_sprite;
    float foodmarket_button_x;
    float foodmarket_button_y;
    float play_button_x;
    float play_button_y;
    float stock_button_x;
    float stock_button_y;

    int statics_button_x;
    int statics_button_y;

    int sleep_button_x;
    int sleep_button_y;

    PetUI(Context c){
        foodmarket_button = BitmapFactory.decodeResource(c.getResources(), R.drawable.store_button);
        play_button = BitmapFactory.decodeResource(c.getResources(), R.drawable.play_image);
        stock_button = BitmapFactory.decodeResource(c.getResources(), R.drawable.stock_image);
        wakeup_button_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.wakeup_image);
        sleep_button_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.sleep_image);
        statics_button_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.statics_image);

        idle = BitmapFactory.decodeResource(c.getResources(), R.drawable.smile_mine_happy);
        sad_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.smile_mine_sad);
        sleep_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.smile_mine_sleepy);
        tired_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.smile_mine_tired);
        dying_sprite = BitmapFactory.decodeResource(c.getResources(), R.drawable.smile_mine_dying);

        image = idle;
        sleep_button = sleep_button_sprite;
    }
}
