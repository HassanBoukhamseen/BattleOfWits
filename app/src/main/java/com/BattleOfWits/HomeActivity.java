package com.BattleOfWits;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {
    private Button Gamestart, settings;
    private TextView Score, Record;
    private int record, score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Gamestart = findViewById(R.id.GameStart);
        Score = findViewById(R.id.Score);
        Record = findViewById(R.id.Record);
        score = GameActivity.count;
        Score.setText(score + "");
        record = GameActivity.temp;
        Record.setText(Math.max(record, score) + "");
        if (GameActivity.count >= GameActivity.temp) {
            GameActivity.temp = GameActivity.count;
        }
        Gamestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.count = 0;
                Intent settings = new Intent(HomeActivity.this, CustomizationActivity.class);
                startActivity(settings);

            }
        });


    }
}
