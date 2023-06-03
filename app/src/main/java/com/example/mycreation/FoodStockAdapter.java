package com.example.mycreation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodStockAdapter extends ArrayAdapter<MyFood> {
    FoodStockActivity activity;

//    TextView count;

    public FoodStockAdapter(Context c, MyFood[] arr){
        super(c, R.layout.adapter_stock_item, arr);
        activity = (FoodStockActivity) c;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MyFood food = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_stock_item, null);
        }

        ((TextView) convertView.findViewById(R.id.name)).setText(food.name);
        ((TextView) convertView.findViewById(R.id.description)).setText(String.valueOf(food.description));
        ((TextView) convertView.findViewById(R.id.params)).setText(food.param);
//        count = ((TextView) convertView.findViewById(R.id.count));

        if (food.name.equals("Жареная курица")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.chicken);
            ((TextView) convertView.findViewById(R.id.count)).setText("Осталось: " + activity.food_info.get("chicken"));
        }
        else if (food.name.equals("Торт")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.cake);
            ((TextView) convertView.findViewById(R.id.count)).setText("Осталось: " + activity.food_info.get("cake"));
        }
        else if (food.name.equals("Бутерброд")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.sandwich);
            ((TextView) convertView.findViewById(R.id.count)).setText("Осталось: " + activity.food_info.get("sandwich"));
        }
        else if (food.name.equals("Пицца")){
            ((ImageView) convertView.findViewById(R.id.food_image)).setImageResource(R.drawable.pizza);
            ((TextView) convertView.findViewById(R.id.count)).setText("Осталось: " + activity.food_info.get("pizza"));
        }

        Button b = (Button) convertView.findViewById(R.id.use);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.name.equals("Жареная курица") && activity.food_info.get("chicken") - 1 >= 0){
                    activity.food_info.put("chicken", activity.food_info.get("chicken") - 1);
                }
                if (food.name.equals("Торт")  && activity.food_info.get("cake") - 1 >= 0){
                    activity.food_info.put("cake", activity.food_info.get("cake") - 1);
                }
                if (food.name.equals("Бутерброд") && activity.food_info.get("sandwich") - 1 >= 0){
                    activity.food_info.put("sandwich", activity.food_info.get("sandwich") - 1);
                }
                if (food.name.equals("Пицца") && activity.food_info.get("pizza") - 1 >= 0){
                    activity.food_info.put("pizza", activity.food_info.get("pizza") - 1);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}