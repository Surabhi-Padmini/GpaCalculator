package com.example.gpacalculator;
import android.text.TextWatcher;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText[] gradeFields = new EditText[5];
    private TextView gpaDisplay;
    private Button computeButton;
    private boolean isCalculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradeFields[0] = findViewById(R.id.grade1);
        gradeFields[1] = findViewById(R.id.grade2);
        gradeFields[2] = findViewById(R.id.grade3);
        gradeFields[3] = findViewById(R.id.grade4);
        gradeFields[4] = findViewById(R.id.grade5);
        gpaDisplay = findViewById(R.id.gpaDisplay);
        computeButton = findViewById(R.id.computeButton);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCalculated) {
                    clearForm();
                } else {
                    calculateGPA();
                }
            }
        });

        for (EditText editText : gradeFields) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (isCalculated) {
                        computeButton.setText("Compute GPA");
                        isCalculated = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void calculateGPA() {
        float total = 0;
        boolean allFilled = true;
        for (EditText gradeField : gradeFields) {
            String gradeStr = gradeField.getText().toString();
            if (gradeStr.isEmpty()) {
                gradeField.setBackgroundColor(Color.RED);
                allFilled = false;
            } else {
                float grade = Float.parseFloat(gradeStr);
                total += grade;
                gradeField.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        if (allFilled) {
            float gpa = total / gradeFields.length;
            gpaDisplay.setText(String.format("GPA: %.2f", gpa));
            changeBackgroundBasedOnGPA(gpa);
            computeButton.setText("Clear Form");
            isCalculated = true;
        }
    }

    private void clearForm() {
        for (EditText gradeField : gradeFields) {
            gradeField.setText("");
            gradeField.setBackgroundColor(Color.TRANSPARENT);
        }
        gpaDisplay.setText("");
        computeButton.setText("Compute GPA");
        findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.WHITE);
        isCalculated = false;
    }

    private void changeBackgroundBasedOnGPA(float gpa) {
        if (gpa < 60) {
            findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.RED);
        } else if (gpa >= 60 && gpa < 80) {
            findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.YELLOW);
        } else {
            findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.GREEN);
        }
    }
}
