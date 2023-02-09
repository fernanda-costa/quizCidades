package br.ufpr.quizcidades;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String IMAGE_BASE_URL = "http://31.220.51.31/ufpr/cidades/";
    public static final String NUMERO_ACERTOS = "NUMERO_ACERTOS";

    Map<Integer, String> cities;
    Map<Integer, String> randomCities;

    List<Integer> keys;
    String city;
    String imageCity;

    int acertos = 0;
    int respondido = 0;

    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = initializeCities();
        randomCities = getRandomCities();
        keys = new ArrayList<Integer>( randomCities.keySet());

        getRandomCity(keys.get(0));
        TextView answerResultTextView = findViewById(R.id.answerResultTextView);
        answerResultTextView.setText(city);
    }

    public void checkAnswer(View view) {
        EditText editTextAnswer = findViewById(R.id.editTextTentativa);
        Button checkButton = findViewById(R.id.checkButton);
        answered = !answered;
        if(answered) {
            showAnswer();
            nextCity();
            editTextAnswer.setVisibility(View.INVISIBLE);
            checkButton.setText("Próxima cidade");
        } else {
            editTextAnswer.setVisibility(View.VISIBLE);
            checkButton.setText("Corrigir");
        }
    }

    private void getRandomCity(int cityKey) {
        city = randomCities.get(cityKey);
        imageCity = IMAGE_BASE_URL + String.format("0" + String.valueOf(cityKey) + "_" + city.replace(" ", "") + ".jpg");
        ImageView imageView = findViewById(R.id.imageView);
        DownloadTask task = new DownloadTask(this, imageView);
        task.execute(imageCity);
    }

    private void showAnswer() {
        EditText editTextAnswer = findViewById(R.id.editTextTentativa);
        TextView answerResultTextView = findViewById(R.id.answerResultTextView);
        String answer = editTextAnswer.getText().toString();

        boolean acertou = answer.toLowerCase().equals(city.toLowerCase());

        if(acertou) {
            acertos++;
            answerResultTextView.setText("Parabéns, você acertou!!");
            answerResultTextView.setTextColor(Color.parseColor("#00FF00"));
        } else {
            answerResultTextView.setText("Quase! A resposta correta era " + city);
            answerResultTextView.setTextColor(Color.parseColor("#f44336"));
        }
    }

    private void showFinalResult() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(NUMERO_ACERTOS, acertos);
        startActivity(intent);
    }

    private void nextCity() {
        respondido++;
        if(respondido == 4) {
            showFinalResult();
        } else {
            getRandomCity(keys.get(respondido));
            EditText editTextAnswer = findViewById(R.id.editTextTentativa);
            editTextAnswer.setText("");
            TextView answerResultTextView = findViewById(R.id.answerResultTextView);
            answerResultTextView.setText(city);
        }
    }

    private void getCityImage() {

    }

    private Map<Integer, String> getRandomCities() {
        randomCities = new HashMap<>();
       while(randomCities.size() != 4) {
            int randomIndex = (int) Math.floor(Math.random() * 10) + 1;
            randomCities.put(randomIndex, cities.get(randomIndex));
        }
        return randomCities;
    }

    private Map<Integer, String> initializeCities() {
        return new HashMap<Integer, String>() {{
            put(1, "barcelona");
            put(2, "brasilia");
            put(3, "curitiba");
            put(4, "las vegas");
            put(5, "montreal");
            put(6, "paris");
            put(7, "rio de janeiro");
            put(8, "salvador");
            put(9, "sao paulo");
            put(10, "toquio");
        }};
    }
}

