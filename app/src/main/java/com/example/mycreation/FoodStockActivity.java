package com.example.mycreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;

public class FoodStockActivity extends Activity {
    HashMap<String, Integer> food_info = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodstock);

        ListView listView = findViewById(R.id.listView);
        Button back = findViewById(R.id.back);

        food_info.put("chicken", Integer.parseInt(getIntent().getStringExtra("chicken")));
        food_info.put("cake", Integer.parseInt(getIntent().getStringExtra("cake")));
        food_info.put("sandwich", Integer.parseInt(getIntent().getStringExtra("sandwich")));
        food_info.put("pizza", Integer.parseInt(getIntent().getStringExtra("pizza")));

        FoodStockAdapter adapter = new FoodStockAdapter(this, makeFood());
        listView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("chicken", Integer.toString(food_info.get("chicken")));
                i.putExtra("cake", Integer.toString(food_info.get("cake")));
                i.putExtra("sandwich", Integer.toString(food_info.get("sandwich")));
                i.putExtra("pizza", Integer.toString(food_info.get("pizza")));
                setResult(3, i);
                finish();
            }
        });
    }

    MyFood[] makeFood(){
        MyFood[] arr = new MyFood[4];

        String[] foodArr = {"Жареная курица", "Торт", "Бутерброд", "Пицца"};
        String[] descArr = {"Хорошо прожаренная ножка курицы",
                "Если хочется чего-нибудь сладкого", "Просто обычный бутерброд", "Здесь только пепперони, тесто и ничего больше"};
        String[] paramArr = {"Здоровье: +45 | Счастье: +30 | Сытость: +70",
                "Здоровье: +30 | Счастье: +150 | Сытость: +50",
                "Здоровье: +20 | Счастье: +30 | Сытость: +40",
                "Здоровье: +150 | Счастье: +100 | Сытость: +100"};
        Integer[] countArr = {food_info.get("chicken"),
                food_info.get("cake"),
                food_info.get("sandwich"),
                food_info.get("pizza")};


        for (int i = 0; i < arr.length; i++){
            MyFood food = new MyFood();
            food.name = foodArr[i];
            food.description = descArr[i];
            food.param = paramArr[i];
            food.count = countArr[i];
            arr[i] = food;
        }
        return arr;
    }
}
