package com.BattleOfWits;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeActivity extends AppCompatActivity {
    private Button gamestart, settings;
    private TextView scoreTextView, recordTextView;
    private int record, score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gamestart = findViewById(R.id.GameStart);
        scoreTextView = findViewById(R.id.Score);
        recordTextView = findViewById(R.id.Record);
        score = GameActivity.count;
        scoreTextView.setText(score + "");
        record = GameActivity.temp;
        recordTextView.setText(Math.max(record, score) + "");
        if (GameActivity.count >= GameActivity.temp) {
            GameActivity.temp = GameActivity.count;
        }
        gamestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.count = 0;
                Intent settings = new Intent(HomeActivity.this, CustomizationActivity.class);
                startActivity(settings);

            }
        });


    }
}
