package com.BattleOfWits;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
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
import android.os.Vibrator;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;


public class GameActivity extends CustomizationActivity {

    private RequestQueue queueRequest;
    private TextView allQuestions, timerText;
    private Button answer1, answer2, answer3, answer4, nextQuestion;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String question;
    private String correctAnswer;
    private String difficulty;
    static String diff = "medium";
    private String category;
    private Random rand = new Random();
    static int count = 0;
    static int i = 0;
    static int temp = 0;
    static int index;
    static int time = 0;
    static int quest = 0;
    int numberOfQ = 15;
    int timePerQ = 10;
    int seconds = 10;
    private CountDownTimer timer;
    int s = 1;
    List<String> answers = new ArrayList<>(Arrays.asList("0", "1", "2", "3"));

    private void shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 29) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createPredefined(2));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(20,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        }

    }

    private void slightShake() {
        if (Build.VERSION.SDK_INT >= 29) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createPredefined(1));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(20,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        queueRequest = Volley.newRequestQueue(this);

        allQuestions = findViewById(R.id.Question);
        answer1 = findViewById(R.id.Answer1);
        answer2 = findViewById(R.id.Answer2);
        answer3 = findViewById(R.id.Answer3);
        answer4 = findViewById(R.id.Answer4);

        allQuestions.setVisibility(View.GONE);
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);

        timerText = findViewById(R.id.Timer);
        timerText.setTextColor(WHITE);

        Intent game = getIntent();
        quest = game.getIntExtra("Questions", numberOfQ);
        time = game.getIntExtra("Time", timePerQ);
        diff = game.getStringExtra("Difficulty");
        category = "category=" + game.getStringExtra("Category");



        final Intent Home = new Intent(GameActivity.this, HomeActivity.class);
        timer = new CountDownTimer( time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(seconds + " Seconds Left");
                seconds -= 1;
                if (seconds == -2) {
                    onFinish();
                }
            }

            @Override
            public void onFinish() {
                seconds = time;
                timerText.setText(seconds + " Seconds Left");
                JsonParse();
            }

        };
        final CountDownTimer t = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                s -= 8;
                timer.cancel();
                if (s == -16) {
                    onFinish();
                }
            }

            @Override
            public void onFinish() {
                if (index == quest) {
                    index = 0;
                    startActivity(Home);
                } else {
                    JsonParse();
                }
            }
        };

        nextQuestion = findViewById(R.id.NextQuestion);
        nextQuestion.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                seconds = time;
                nextQuestion.setVisibility(View.GONE);
                JsonParse();
                allQuestions.setVisibility(View.VISIBLE);
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.VISIBLE);
            }
        });


        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seconds = time;
                nextQuestion.setVisibility(View.GONE);
                if (answer1.getText().toString().equals(correctAnswer)) {
                    answer1.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    count++;
                    shakeItBaby();
                } else if (!(answer1.getText().toString().equals(correctAnswer))) {
                    answer1.setBackgroundTintList(ColorStateList.valueOf(RED));
                    slightShake();
                    if (answer2.getText().toString().equals(correctAnswer)) {
                        answer2.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer3.getText().toString().equals(correctAnswer)) {
                        answer3.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer4.getText().toString().equals(correctAnswer)) {
                        answer4.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    }
                }
                index++;
                t.start();
                i++;
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seconds = time;
                nextQuestion.setVisibility(View.GONE);
                if (answer2.getText().toString().equals(correctAnswer)) {
                    answer2.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    count++;
                    shakeItBaby();
                } else if (!(answer2.getText().toString().equals(correctAnswer))) {
                    answer2.setBackgroundTintList(ColorStateList.valueOf(RED));
                    slightShake();
                    if (answer1.getText().toString().equals(correctAnswer)) {
                        answer1.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer3.getText().toString().equals(correctAnswer)) {
                        answer3.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer4.getText().toString().equals(correctAnswer)) {
                        answer4.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    }
                }
                index++;
                t.start();
                i++;
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seconds = time;
                nextQuestion.setVisibility(View.GONE);
                if (answer3.getText().toString().equals(correctAnswer)) {
                    answer3.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    count++;
                    shakeItBaby();
                } else if (!(answer3.getText().toString().equals(correctAnswer))) {
                    answer3.setBackgroundTintList(ColorStateList.valueOf(RED));
                    slightShake();
                    if (answer2.getText().toString().equals(correctAnswer)) {
                        answer2.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer1.getText().toString().equals(correctAnswer)) {
                        answer1.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer4.getText().toString().equals(correctAnswer)) {
                        answer4.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    }
                }
                index++;
                t.start();
                i++;
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seconds = time;
                nextQuestion.setVisibility(View.GONE);

                if (answer4.getText().toString().equals(correctAnswer)) {
                    answer4.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    count++;
                    shakeItBaby();
                } else if (!(answer4.getText().toString().equals(correctAnswer))) {
                    answer4.setBackgroundTintList(ColorStateList.valueOf(RED));
                    slightShake();
                    if (answer2.getText().toString().equals(correctAnswer)) {
                        answer2.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer3.getText().toString().equals(correctAnswer)) {
                        answer3.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    } else if (answer1.getText().toString().equals(correctAnswer)) {
                        answer1.setBackgroundTintList(ColorStateList.valueOf(GREEN));
                    }
                }
                index++;
                t.start();
                i++;
            }
        });
    }

    private void JsonParse() {
        final String url = "https://opentdb.com/api.php?amount=50&" + category + "&type=multiple&encode=base64";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray Array = response.getJSONArray("results");
                            for (int i = 0; i < Array.length(); i++) {
                                JSONObject result = Array.getJSONObject(rand.nextInt(50));
                                difficulty = result.getString("difficulty");
                                byte[] dfclty = Base64.getDecoder().decode(difficulty);
                                difficulty = new String(dfclty);

                                if (diff.equals(difficulty)) {

                                    question = result.getString("question");
                                    byte[] actualByte = Base64.getDecoder().decode(question);
                                    question = new String(actualByte);
                                    correctAnswer = result.getString("correct_answer");
                                    byte[] ca = Base64.getDecoder().decode(correctAnswer);
                                    correctAnswer = new String(ca);
                                    answers.set(3, correctAnswer);
                                    JSONArray incorrectAnswer = result.getJSONArray("incorrect_answers");
                                    System.out.println(difficulty);
                                    System.out.println(category);

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
                            }

                            // Loop to shuffle the answers.
                            for (int i = 0; i < 3; i++) {
                                Collections.shuffle(answers);
                            }

                            allQuestions.setText(question);
                            answer1.setText(answers.get(2));
                            answer2.setText(answers.get(0));
                            answer3.setText(answers.get(1));
                            answer4.setText(answers.get(3));

                            int ourColor = Color.parseColor("#0F709C");
                            answer1.setBackgroundTintList(ColorStateList.valueOf(ourColor));
                            answer2.setBackgroundTintList(ColorStateList.valueOf(ourColor));
                            answer3.setBackgroundTintList(ColorStateList.valueOf(ourColor));
                            answer4.setBackgroundTintList(ColorStateList.valueOf(ourColor));

                            timer.cancel();
                            timer.start();
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (i == 4) {
                    i = 0;
                    Intent home = new Intent(GameActivity.this, HomeActivity.class);
                    startActivity(home);
                }
                String[] question1 = {"What is the average grade of CS125?", "B", "A", "C", "D"};
                String[] question2 = {"How much is the Final Project grade worth?", "20 %", "10 %", "15 %", "Its just extra credit"};
                String[] question3 = {"How much Are quizzes worth?", "20%", "22%", "24%", "30%"};
                String[] question4 = {"How many MP drops do we have this semester?", "5", "2", "None", "1"};
                String[][] allQuest = {question1, question2, question3, question4};
                if (i == 0) {
                    correctAnswer = question1[2];
                } else if (i == 1) {
                    correctAnswer = question2[2];
                } else if (i == 2) {
                    correctAnswer = question3[3];
                } else {
                    correctAnswer = question4[4];
                }
                allQuestions.setText(allQuest[i][0]);
                answer1.setText(allQuest[i][1]);
                answer2.setText(allQuest[i][2]);
                answer3.setText(allQuest[i][3]);
                answer4.setText(allQuest[i][4]);


                int ourColor = Color.parseColor("#0F709C");
                answer1.setBackgroundTintList(ColorStateList.valueOf(ourColor));
                answer2.setBackgroundTintList(ColorStateList.valueOf(ourColor));
                answer3.setBackgroundTintList(ColorStateList.valueOf(ourColor));
                answer4.setBackgroundTintList(ColorStateList.valueOf(ourColor));

                timer.cancel();
                timer.start();

            }
        });
        queueRequest.add(request);
    }

}

