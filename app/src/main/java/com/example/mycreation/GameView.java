package com.example.mycreation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.metrics.PlaybackErrorEvent;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {

    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }
        @Override
        public void onFinish() {
        }
    }
    private int viewWidth;
    private int viewHeight;
    int points = 1;
    private final int timerInterval = 30;

    private int pipe_count_arrive = 30;

    private SmileFlipper player;
    private Rect firstFramePipe;
    private Bitmap pipe_bitmap;

    private ArrayList<Pipe> pipes = new ArrayList<>();

    private Canvas global_canvas;

    int height;
    int width;

    public GameView(Context context) {
        super(context);
        Bitmap player_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flying_smile);
        pipe_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pipe);
        int w_player = player_bitmap.getWidth();
        int h_player = player_bitmap.getHeight();
        int w_pipe = pipe_bitmap.getWidth();
        int h_pipe = pipe_bitmap.getHeight();
        Rect firstFramePlayer = new Rect(0, 0, w_player, h_player);
        firstFramePipe = new Rect(0, 0, w_pipe, h_pipe);
        player = new SmileFlipper(300, 500, 0, 750, firstFramePlayer, player_bitmap);
        Timer t = new Timer();
        t.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        global_canvas = canvas;
        canvas.drawARGB(250, 127, 199, 255);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(55.0f);
        p.setColor(Color.WHITE);
        canvas.drawText("Заработанные монеты: " + points, 0, 50, p);
        player.draw(canvas);
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).draw(global_canvas);
        }
    }

    protected void update () {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == 2){
            int o = height;
            height = Math.abs(width - player.getFrameHeight() * 3);
            width = o;
        }
        else{
            height = getHeight() - player.getFrameHeight() * 3;
            width = getWidth();
        }
        player.update(timerInterval);
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).update(timerInterval);
            if (player.intersect(pipes.get(i))) {
                if (points > 0) {
                    points--;
                }
                pipes.remove(i);
                continue;
            }
            if (pipes.get(i).getX() <= 0 - pipes.get(i).getPipe_width()) {
                pipes.remove(i);
                points++;
            }
        }

        invalidate();

        if (player.jump_count > 0){
            player.setY(player.getY() - player.jump_force);
            player.jump_count -= 1;
            if (player.jump_count <= 0){
                player.jump_ended = true;
            }
            return;
        }
        else if (player.jump_count <= 0 && player.jump_ended){
            if (player.hold_in_air > 0) {
                player.hold_in_air = 0;
                player.setVy(0);
            }
        }


        if (player.getY() <= 0 || player.getY() >= getHeight() - player.getFrameHeight()){
            if (points > 0){
                points--;
            }
            player.setY(100);
        }

        pipe_count_arrive--;

        if (pipe_count_arrive <= 0){
            pipe_count_arrive = 60;
            Random random = new Random();
            int randomNumber = random.nextInt(Math.abs(height + 1)) + player.getFrameHeight() * 3;
            if (randomNumber > height) {
                randomNumber -= player.getFrameHeight() * 3;
                randomNumber = Math.abs(randomNumber);
            }
            if (randomNumber < 500){
                randomNumber += 500;
            }
            pipes.add(new Pipe(width + 600, randomNumber - pipe_bitmap.getHeight() - player.getFrameHeight() * 3, 600, 0, firstFramePipe, pipe_bitmap, "upper"));
            pipes.add(new Pipe(width + 600, randomNumber, 600, 0, firstFramePipe, pipe_bitmap, "bottom"));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN) {
            player.jump_count += 5;
        }
        return true;
    }

    void exit_from_game(){
        Intent i = new Intent();
        i.putExtra("money", Integer.toString(points));
        ((FlappySmileActivity)getContext()).setResult(4, i);
        ((FlappySmileActivity)getContext()).finish();
    }
}