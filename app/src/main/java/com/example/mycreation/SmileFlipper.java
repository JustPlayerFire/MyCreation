package com.example.mycreation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class SmileFlipper {
    private Bitmap bitmap;
    private List<Rect> frames;

    private int frameWidth;
    private int frameHeight;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;

    private double x = 100;
    private double y;

    private double velocityX;
    private double velocityY;

    private int padding;

    int jump_count = 0;
    int jump_force = 75;
    int hold_in_air = 50;
    int expand_Vy;
    private int gravity;

    boolean jump_ended = false;

    public SmileFlipper(double x, double y, double velocityX, double velocityY,
                  Rect initialFrame, Bitmap bitmap){

        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.gravity = (int) velocityY;
        this.bitmap = bitmap;
        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);
        this.bitmap = bitmap;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
        this.padding = 20;
        this.expand_Vy = gravity / hold_in_air;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public double getVx() {
        return velocityX;
    }

    public void setVx(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVy() {
        return velocityY;
    }

    public void setVy(double velocityY) {
        this.velocityY = velocityY;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame%frames.size();
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    public int getFramesCount () {
        return frames.size();
    }

    public void addFrame (Rect frame) {
        frames.add(frame);
    }

    public void update (int ms) {

        timeForCurrentFrame += ms;
        if (hold_in_air * expand_Vy <= gravity){
            velocityY += expand_Vy;
            hold_in_air += 1;
        }
        else if (hold_in_air * expand_Vy >= gravity){
            jump_ended = false;
        }
        y = y + velocityY * ms/1000.0;
    }

    public void draw (Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect((int)x, (int)y, (int)(x + frameWidth), (int)(y + frameHeight));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination,  p);
    }

    public Rect getBoundingBoxRect () {
        return new Rect((int)x+padding, (int)y+padding, (int)(x + frameWidth - 2 *padding),
                (int)(y + frameHeight - 2 * padding));
    }

    public boolean intersect (Pipe s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }
}
