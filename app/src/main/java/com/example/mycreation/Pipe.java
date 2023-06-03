package com.example.mycreation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Pipe {
    private Bitmap bitmap;
    private List<Rect> frames;

    private int frameWidth;
    private int frameHeight;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;

    private double x = 1000;
    private double y;

    private double velocityX;
    private double velocityY;

    private int padding;

    private String type;

    private int pipe_width;

    private int pipe_height;

    private boolean isDestroyed = false;


    public Pipe(double x, double y, double velocityX, double velocityY,
                        Rect initialFrame, Bitmap bitmap, String type){

        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bitmap = bitmap;
        this.type = type;
        if (type.equals("upper")){
            Matrix matrix = new Matrix();
            matrix.postScale(1, -1);
            matrix.postTranslate( 0, bitmap.getHeight());
            this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        this.frames = new ArrayList<>();
        this.frames.add(initialFrame);
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.frameWidth = initialFrame.width();
        this.frameHeight = initialFrame.height();
        this.padding = 20;
        this.pipe_width = bitmap.getWidth();
        this.pipe_height = bitmap.getHeight();
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

//        timeForCurrentFrame += ms;
//
//        if (timeForCurrentFrame >= frameTime) {
//            currentFrame = (currentFrame + 1) % frames.size();
//            timeForCurrentFrame = timeForCurrentFrame - frameTime;
//        }

        x = x - velocityX * ms/1000.0;
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

    public boolean intersect (SmileFlipper s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }

    public int getPipe_width() {
        return pipe_width;
    }

    public int getPipe_height() {
        return pipe_height;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
