package com.BattleOfWits;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class GameActivity extends HomeActivity {
    private RequestQueue QueueRequest;
    private TextView Questions;
    private Button Answer1, Answer2, Answer3, Answer4, NextQuestion;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String question;
    private String correctAnswer;
    private Random rand = new Random();
    static int count = 0;
    static int temp = 0;
    static int Index;
    List<String> answers = new ArrayList<>(Arrays.asList("0", "1", "2", "3"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        QueueRequest = Volley.newRequestQueue(this);
        Questions = findViewById(R.id.Question);
        Answer1 = findViewById(R.id.Answer1);
        Answer2 = findViewById(R.id.Answer2);
        Answer3 = findViewById(R.id.Answer3);
        Answer4 = findViewById(R.id.Answer4);
        NextQuestion = findViewById(R.id.NextQuestion);
        NextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonParse();
                NextQuestion.setVisibility(View.GONE);
            }
        });
        final Intent Home = new Intent(GameActivity.this, HomeActivity.class);

        Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Answer1.getText().toString().equals(correctAnswer)) {
                    count++;
                }
                if (Index % 15 == 0) {
                    startActivity(Home);
                }
                JsonParse();
            }
        });
        Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Answer2.getText().toString().equals(correctAnswer)) {
                    count++;
                }
                if (Index % 15 == 0) {
                    startActivity(Home);
                }
                JsonParse();
            }
        });
        Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Answer3.getText().toString().equals(correctAnswer)) {
                    count++;
                }
                if (Index % 15 == 0) {
                    startActivity(Home);
                }
                JsonParse();
            }
        });
        Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Answer4.getText().toString().equals(correctAnswer)) {
                    count++;
                }
                if (Index % 15 == 0) {
                    startActivity(Home);
                }
                JsonParse();
            }
        });
    }

    private void JsonParse() {
        final String url = "https://opentdb.com/api.php?amount=50&type=multiple&encode=base64";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray Array = response.getJSONArray("results");
                            for (int i = 0; i < Array.length(); i++) {
                                JSONObject result = Array.getJSONObject(rand.nextInt(50));
                                question = result.getString("question");
                                byte[] actualByte = Base64.getDecoder().decode(question);
                                question = new String(actualByte);
                                correctAnswer = result.getString("correct_answer");
                                byte[] ca = Base64.getDecoder().decode(correctAnswer);
                                correctAnswer = new String(ca);
                                answers.set(3, correctAnswer);
                                JSONArray incorrectAnswer = result.getJSONArray("incorrect_answers");
                                for (int j = 0; j < incorrectAnswer.length(); j++) {
                                    firstAnswer = incorrectAnswer.getString(0);
                                    byte[] fa = Base64.getDecoder().decode(firstAnswer);
                                    firstAnswer = new String(fa);
                                    answers.set(0, firstAnswer);
                                    secondAnswer = incorrectAnswer.getString(1);
                                    byte[] sa = Base64.getDecoder().decode(secondAnswer);
                                    secondAnswer = new String(sa);
                                    answers.set(1, secondAnswer);
                                    thirdAnswer = incorrectAnswer.getString(2);
                                    byte[] ta = Base64.getDecoder().decode(thirdAnswer);
                                    thirdAnswer = new String(ta);
                                    answers.set(2, thirdAnswer);
                                }
                            }

                            Collections.shuffle(answers);
                            Collections.shuffle(answers);
                            Collections.shuffle(answers);
                            Questions.setText(question);
                            Answer1.setText(answers.get(2));
                            Answer2.setText(answers.get(0));
                            Answer3.setText(answers.get(1));
                            Answer4.setText(answers.get(3));
                            Index++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        QueueRequest.add(request);
    }
}
