package com.example.mycreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class FoodMarketActivity extends Activity {
    HashMap<String, Integer> food_info = new HashMap<>();
    TextView money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmarket);

        Button exit_button = findViewById(R.id.exit);

        FoodMarketAdapter adapter = new FoodMarketAdapter(this, makeFood());
        ListView lv = (ListView) findViewById(R.id.listView);
        money = (TextView) findViewById(R.id.money);
        lv.setAdapter(adapter);

        food_info.put("chicken", 0);
        food_info.put("cake", 0);
        food_info.put("sandwich", 0);
        food_info.put("pizza", 0);
        food_info.put("money", Integer.parseInt(getIntent().getStringExtra("money")));
        money.setText("Монеты: " + food_info.get("money"));

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("chicken", Integer.toString(food_info.get("chicken")));
                i.putExtra("cake", Integer.toString(food_info.get("cake")));
                i.putExtra("sandwich", Integer.toString(food_info.get("sandwich")));
                i.putExtra("pizza", Integer.toString(food_info.get("pizza")));
                i.putExtra("money", Integer.toString(food_info.get("money")));
                System.out.println(food_info.get("chicken") + " " +
                        food_info.get("cake") + " " +
                        food_info.get("sandwich") + " " +
                        food_info.get("pizza"));
                setResult(2, i);
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
        int[] priceArr = {49, 39, 10, 69};

        for (int i = 0; i < arr.length; i++){
            MyFood food = new MyFood();
            food.name = foodArr[i];
            food.description = descArr[i];
            food.param = paramArr[i];
            arr[i] = food;
            food.price = priceArr[i];
        }
        return arr;
    }
}
