package com.example.experiment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int JAP_REQUEST = 1;

    public int eCount = battlefield1.getEliminateCount();

    //SharedPreferences.Editor eEditor = battlefield.getEditor();

    public MainActivity() throws IllegalAccessException, InstantiationException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG","Main_onCreate");
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.eliminateCountText) ;

        //使用SharedPreferences类来储存击杀数.
        SharedPreferences sharedPreferences = getSharedPreferences("counter",MODE_PRIVATE);
        eCount = sharedPreferences.getInt("eliminateCount",eCount);

        Intent intent = getIntent();
        String JAP_COUNT = String.valueOf(intent.getIntExtra("counter",eCount));
        textView.setText("击杀敌人数:"+JAP_COUNT);
        Button gotoBattlefield = (Button) findViewById(R.id.goToBattlefieldButton1);
        gotoBattlefield.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, battlefield1.class);
            startActivity(intent1);
        });

        //击杀数归零按键,按下后即可使击杀数归零.
        Button buttonZero = (Button) findViewById(R.id.buttonZero);
        buttonZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("counter", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("eliminateCount", 0);
                editor.apply();

                //在按下按键后,事实上eliminateCount已经变成了0,但是显示时仍然是当前击杀数,所以要重新setText,这样即可即时清空击杀数.
                textView.setText("击杀敌人数:"+0);
            }
        });
    }
}