package com.example.testedittext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.constraint);
        Button button = (Button) findViewById(R.id.button);

        List<EditText> editTexts = new ArrayList<>();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(MainActivity.this);
                editText.setText("1");
                editText.setId(View.generateViewId());
                editTexts.add(editText);

                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                if (editTexts.size() == 1){
                    layoutParams.topToBottom = R.id.button;
                    constraintLayout.addView(editText,layoutParams);
                }else {
                    layoutParams.topToBottom = editTexts.get(editTexts.size() - 2).getId();
                    constraintLayout.addView(editText,layoutParams);

                }
            }
        });
    }
}