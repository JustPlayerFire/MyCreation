package com.example.mycreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class CreatePetActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        EditText enter_name = findViewById(R.id.name_enter);
        Button ok = findViewById(R.id.name_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("name", enter_name.getText().toString());
                setResult(5, i);
                finish();
            }
        });
    }
}
