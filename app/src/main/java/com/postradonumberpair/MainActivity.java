package com.postradonumberpair;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    int[] btns = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20, R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24, R.id.btn25, R.id.btn26, R.id.btn27, R.id.btn28, R.id.btn29, R.id.btn30, R.id.btn31, R.id.btn32, R.id.btn33, R.id.btn34, R.id.btn35, R.id.btn36, R.id.btn37, R.id.btn38, R.id.btn39, R.id.btn40, R.id.btn41, R.id.btn42, R.id.btn43, R.id.btn44, R.id.btn45, R.id.btn46, R.id.btn47, R.id.btn48, R.id.btn49, R.id.btn50};
    TextView Score;
    private int score = 0;
    Button playRestartBtn;
    int flag = 0;
    ArrayList<Integer> Numbers = new ArrayList<>();
    ArrayList<Integer> matchedIndexes = new ArrayList<>();
    Button btn;
    private Button clicked1;
    private Button clicked2;
    private boolean gameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Score = (TextView) findViewById(R.id.lblScore);
        playRestartBtn = (Button) findViewById(R.id.playBtn);
        disableButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void btnClick(View view) {
        Button Btn = (Button) findViewById(view.getId());
        int index = buttonIndex(Btn);

        if (clicked1 == null) {
            clicked1 = Btn;
            clicked1.setText(String.valueOf(Numbers.get(index)));
            clicked1.setBackgroundColor(Color.WHITE);
            clicked1.setTextColor(Color.BLACK);
        } else if (clicked2 == null && Btn != clicked1) {
            clicked2 = Btn;
            clicked2.setText(String.valueOf(Numbers.get(index)));
            clicked2.setBackgroundColor(Color.WHITE);
            clicked2.setTextColor(Color.BLACK);

            new Handler().postDelayed(() -> {
                if (isMatch()) {
                    score += 5;
                    Score.setText(String.valueOf(score));
                    matchedIndexes.add(clicked1.getId());
                    matchedIndexes.add(clicked2.getId());
                    clicked1.setClickable(false);
                    clicked2.setClickable(false);
                } else {
                    clicked1.setText("X");
                    clicked2.setText("X");
                    clicked1.setBackgroundColor(Color.BLACK);
                    clicked2.setBackgroundColor(Color.BLACK);
                    clicked1.setTextColor(Color.WHITE);
                    clicked2.setTextColor(Color.WHITE);
                }

                clicked1 = null;
                clicked2 = null;
            }, 500);
        }
    }

    private int buttonIndex(Button button) {
        for (int i = 0; i < btns.length; i++) {
            if (btns[i] == button.getId()) {
                return i;
            }
        }
        return -1;
    }

    private boolean isMatch() {
        int index1 = buttonIndex(clicked1);
        int index2 = buttonIndex(clicked2);
        return Numbers.get(index1).equals(Numbers.get(index2));
    }

    public void StartGame(View view) {

        int x, y;
        Numbers.clear();
        for (y = 1; y <= 2; y++) {
            for (x = 1; x <= 25; x++) {
                Numbers.add(x);
            }
        }
        Collections.shuffle(Numbers);
        matchedIndexes.clear();
        for (int i = 0; i < 50; i++) {
            btn = (Button) findViewById(btns[i]);
            btn.setText("X");
            btn.setBackgroundColor(Color.BLACK);
            btn.setTextColor(Color.WHITE);
        }
        flag = 0;
        score = 0;
        Score.setText(String.valueOf(score));

        enableButtons();
        gameStarted = true;
        playRestartBtn.setText("Restart");
    }

    public void ShowNum(View view) {
        if (!gameStarted) {
            Toast.makeText(getApplicationContext(), "Please click Play first", Toast.LENGTH_SHORT).show();
            return;
        }
        Button btn = (Button) findViewById(view.getId());

        if (flag == 0) {
            // Show numbers
            btn.setText("HIDE");
            flag = 1;
            for (int i = 0; i < btns.length; i++) {
                Button numberButton = findViewById(btns[i]);
                if (!matchedIndexes.contains(btns[i])) {
                    numberButton.setText(String.valueOf(Numbers.get(i)));
                    numberButton.setBackgroundColor(Color.WHITE);
                    numberButton.setTextColor(Color.BLACK);
                    numberButton.setClickable(false);
                }
            }
        } else {
            // Hide numbers
            btn.setText("SHOW");
            flag = 0;
            for (int i = 0; i < btns.length; i++) {
                Button numberButton = findViewById(btns[i]);
                if (!matchedIndexes.contains(btns[i])) {
                    numberButton.setText("X");
                    numberButton.setBackgroundColor(Color.BLACK);
                    numberButton.setTextColor(Color.WHITE);
                    numberButton.setClickable(true);
                }
            }
        }
    }

    private void disableButtons() {
        for (int btnId : btns) {
            Button button = findViewById(btnId);
            button.setClickable(false);
        }
    }

    private void enableButtons() {
        for (int btnId : btns) {
            Button button = findViewById(btnId);
            button.setClickable(true);
        }
    }

}