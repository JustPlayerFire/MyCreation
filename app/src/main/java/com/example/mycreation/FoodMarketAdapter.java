package com.example.mycreation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodMarketAdapter extends ArrayAdapter<MyFood> {
    FoodMarketActivity activity;

    public FoodMarketAdapter(Context c, MyFood[] arr){
        super(c, R.layout.adapter_item, arr);
        activity = (FoodMarketActivity) c;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyFood food = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item, null);
        }

        ((TextView) convertView.findViewById(R.id.name)).setText(food.name);
        ((TextView) convertView.findViewById(R.id.description)).setText(String.valueOf(food.description));
        ((TextView) convertView.findViewById(R.id.params)).setText(food.param);
        ((TextView) convertView.findViewById(R.id.price)).setText("Стоимость: " + Integer.toString(food.price));

        if (food.name.equals("Жареная курица")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.chicken);
        }
        else if (food.name.equals("Торт")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.cake);
        }
        else if (food.name.equals("Бутерброд")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.sandwich);
        }
        else if (food.name.equals("Пицца")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.pizza);
        }
        Button b = (Button) convertView.findViewById(R.id.buy);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.name.equals("Жареная курица")){
                    activity.food_info.put("chicken", activity.food_info.get("chicken") + 1);
                }
                else if (food.name.equals("Торт")){
                    activity.food_info.put("cake", activity.food_info.get("cake") + 1);
                }
                else if (food.name.equals("Бутерброд")){
                    activity.food_info.put("sandwich", activity.food_info.get("sandwich") + 1);
                }
                else if (food.name.equals("Пицца")){
                    activity.food_info.put("pizza", activity.food_info.get("pizza") + 1);
                }
                if (activity.food_info.get("money") - food.price < 0){
                    Toast.makeText(activity, "Недостаточно монет!", Toast.LENGTH_SHORT).show();
                    return;
                }
                activity.food_info.put("money", activity.food_info.get("money") - food.price);
                activity.money.setText("Монеты: " + activity.food_info.get("money"));
            }
        });
        return convertView;
    }
}