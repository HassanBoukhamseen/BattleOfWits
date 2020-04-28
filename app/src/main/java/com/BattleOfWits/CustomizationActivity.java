package com.BattleOfWits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomizationActivity extends AppCompatActivity {
    private EditText numOfQ, timePerQuestion, difficulty;
    private AutoCompleteTextView category;
    private Button set;
    public static int numberOfQ, timePerQ;
    private String n, t, d, c;
    private String[] categories = {"General Knowledge", "Books", "Film", "Music", "Musicals",
            "Television", "Video Games", "Board Games", "Science and Nature", "Computers", "Mathematics", "Mythology",
            "Sports", "Geography", "History", "Politics", "Art", "Celebrities", "Animals", "Vehicles", "Comics",
            "Gadgets", "Anime and Manga", "Cartoon and Animation"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
        numOfQ = findViewById(R.id.numOfQuestions);
        timePerQuestion = findViewById(R.id.timePerQ);
        difficulty = findViewById(R.id.difficulty);
        category = findViewById(R.id.Categ);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, categories);
        category.setAdapter(adapter);
        set = findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whenClicked();
            }
        });
    }

    public String categoryNumber(String s) {
        for (int i = 0; i < categories.length; i++) {
            int n = 9 + i;
            if (categories[i].equals(s)) {
                s = n + "";
            }
        }
        return s;
    }

    public void whenClicked() {
        GameActivity.count = 0;
        final Intent game = new Intent(CustomizationActivity.this, GameActivity.class);
        n = numOfQ.getText().toString();
        t = timePerQuestion.getText().toString();
        d = difficulty.getText().toString();
        c = category.getText().toString();
        c = categoryNumber(c);
        if (!n.isEmpty()) {
            numberOfQ = Integer.parseInt(n);
        } else {
            numberOfQ = 15;
        }
        if (!t.isEmpty()) {
            timePerQ = Integer.parseInt(t);
        } else {
            timePerQ = 10;
        }
        if (d.isEmpty()) {
            d = "medium";
        }

        if (d.equals("Easy") || d.equals("easy")) {
            d = "easy";
            game.putExtra("Questions", numberOfQ);
            game.putExtra("Time", timePerQ);
            game.putExtra("Difficulty", d);
            game.putExtra("Category", c);
            startActivity(game);
            Toast.makeText(CustomizationActivity.this,
                    "Round info changed accordingly", Toast.LENGTH_LONG).show();
        } else if (d.equals("Hard") || d.equals("hard")) {
            d = "hard";
            game.putExtra("Questions", numberOfQ);
            game.putExtra("Time", timePerQ);
            game.putExtra("Difficulty", d);
            game.putExtra("Category", c);
            startActivity(game);
            Toast.makeText(CustomizationActivity.this,
                    "Round info changed accordingly", Toast.LENGTH_LONG).show();
        } else if (d.equals("Medium") || d.equals("medium")) {
            d = "medium";
            game.putExtra("Questions", numberOfQ);
            game.putExtra("Time", timePerQ);
            game.putExtra("Difficulty", d);
            game.putExtra("Category", c);
            startActivity(game);
            Toast.makeText(CustomizationActivity.this,
                    "Round info changed accordingly", Toast.LENGTH_LONG).show();
        } else {
            difficulty.setError("Input Invalid for Difficulty, Please Enter Easy, Medium, Or Hard");
            difficulty.requestFocus();

        }
    }
}
