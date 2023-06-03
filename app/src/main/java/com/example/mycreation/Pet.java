package com.example.mycreation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

public class Pet implements Serializable {

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private long id;

    String name = "Gustavo";
    int health = 150;
    int food = 150;
    int happiness = 150;
    int money = 1500;

    int power = 1000;

    int sleep = 0;

    int count_chicken = 0;
    int count_cake = 0;
    int count_sandwich = 0;
    int count_pizza = 0;

    long time_started;
    long time_finished;

    public long getTime_started() {
        return time_started;
    }

    public void setTime_started(long time_started) {
        this.time_started = time_started;
    }

    public long getTime_finished() {
        return time_finished;
    }

    public void setTime_finished(long time_finished) {
        this.time_finished = time_finished;
    }

    public int getCount_chicken() {
        return count_chicken;
    }

    public void setCount_chicken(int count_chicken) {
        this.count_chicken = count_chicken;
    }

    public int getCount_cake() {
        return count_cake;
    }

    public void setCount_cake(int count_cake) {
        this.count_cake = count_cake;
    }

    public int getCount_sandwich() {
        return count_sandwich;
    }

    public void setCount_sandwich(int count_sandwich) {
        this.count_sandwich = count_sandwich;
    }

    public int getCount_pizza() {
        return count_pizza;
    }

    public void setCount_pizza(int count_pizza) {
        this.count_pizza = count_pizza;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    int alive = 1;
    int x = 232;
    int y = 500;



    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }


    Pet(long id, String name, int health, int happiness, int food, int money, int alive, int power, int sleep,
        int chicken, int cake, int sandwich, int pizza, long time_started, long time_finished){
        this.name = name;
        this.id = id;
        this.health = health;
        this.happiness = happiness;
        this.food = food;
        this.money = money;
        this.alive = alive;
        this.power = power;
        this.sleep = sleep;
        this.count_chicken = chicken;
        this.count_cake = cake;
        this.count_sandwich = sandwich;
        this.count_pizza = pizza;
        this.time_started = time_started;
        this.time_finished = time_finished;
    }

}
