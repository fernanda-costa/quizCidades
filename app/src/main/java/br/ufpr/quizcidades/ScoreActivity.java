package br.ufpr.quizcidades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    public static final String NUMERO_ACERTOS = "NUMERO_ACERTOS";
    public static final int NUMERO_TENTATIVAS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        int acertos = intent.getIntExtra(NUMERO_ACERTOS, -1);

        TextView errosTextView = findViewById(R.id.errosTextView);
        TextView acertosTextView = findViewById(R.id.acertosTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);

        acertosTextView.setText(String.valueOf(acertos));
        errosTextView.setText(String.valueOf(NUMERO_TENTATIVAS - acertos));
        scoreTextView.setText(String.valueOf(acertos * 25));
    }
}