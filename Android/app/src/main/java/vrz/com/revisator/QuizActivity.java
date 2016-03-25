package vrz.com.revisator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import vrz.com.revisator.objects.Question;
import vrz.com.revisator.objects.Quizz;
import vrz.com.revisator.objects.Resultat;
import vrz.com.revisator.tools.VolleyCallback;
import vrz.com.revisator.tools.WebApiHandler;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private String quizId;
    private CheckBox answerOne;
    private ArrayList<CheckBox> checkBoxList;
    private CheckBox answerTwo;
    private CheckBox answerThree;
    private CheckBox answerFour;
    private CheckBox answerFive;
    private CheckBox answerSix;
    private Button validButton;
    private int quEnCours = 0;
    private Resultat result;
    private Quizz quizEnCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialisation des éléments
        questionText = (TextView) findViewById(R.id.questionText);
        checkBoxList = new ArrayList<>();
        answerOne = (CheckBox) findViewById(R.id.awnserOne);
        answerTwo = (CheckBox) findViewById(R.id.answerTwo);
        answerThree = (CheckBox) findViewById(R.id.answerTree);
        answerFour = (CheckBox) findViewById(R.id.answerFour);
        answerFive = (CheckBox) findViewById(R.id.answerFive);
        answerSix = (CheckBox) findViewById(R.id.answerSix);
        checkBoxList.add(answerOne);
        checkBoxList.add(answerTwo);
        checkBoxList.add(answerThree);
        checkBoxList.add(answerFour);
        checkBoxList.add(answerFive);
        checkBoxList.add(answerSix);
        validButton = (Button) findViewById(R.id.validButton);
        result = new Resultat(1);

        // Récupération de l'ID du quiz
        quizId = getIntent().getExtras().getString("quizId");

        // Récupération du quiz
        WebApiHandler.getQuiz(quizId, getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> result) {
            }

            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onSuccess(Quizz result) {
                // Si le quiz est bien récupéré, traitement de la première question
                quizEnCours = result;
                displayQuestion(0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void displayQuestion(final int quNumber) {
        // Récupération de la question
        final Question question = quizEnCours.getQuestion(quNumber);
        // Set up de l'affichage
        setTitle("Question " + String.valueOf((quNumber + 1)) + " / " + String.valueOf(quizEnCours.getNombreQuestion()));
        questionText.setText(question.getEnnonceQuestion());
        for (int i = 0; i < 6; i++) {
            if (i < question.getNbReponses()) {
                checkBoxList.get(i).setText(question.getReponse(i));
                checkBoxList.get(i).setChecked(false);
            }
            else {
                checkBoxList.get(i).setVisibility(View.INVISIBLE);
            }
        }
        // Listener pour le bouton Valider
        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifie que l'utilisateur a bien coché une réponse
                boolean isSomethingChecked = false;
                for(int i = 0; i < 6 ; i++){
                    if(checkBoxList.get(i).isChecked()){
                        isSomethingChecked = true;
                    }
                }
                // Si c'est le cas
                if(isSomethingChecked == true){
                    // On sauvegarde sa réponse
                    saveResultat(quNumber);
                    // Si ce n'est pas la dernière question
                    if (quNumber != (quizEnCours.getNombreQuestion() - 1))
                        // On traite la suivante
                        displayQuestion(quNumber + 1);
                    else{
                        // Si c'est la dernière
                        validButton.setClickable(false);
                        // On calcule le score du quiz
                        int nbBonneReponses = (int) result.getScore(quizEnCours);
                        // On l'envoi au serveur
                        try {
                            WebApiHandler.sendResult(String.valueOf(nbBonneReponses), String.valueOf(quizEnCours.getNombreQuestion()), quizId, getApplicationContext(), new VolleyCallback() {
                                @Override
                                public void onSuccess(HashMap<String, String> result) {

                                }

                                @Override
                                public void onSuccess(String result) {

                                }

                                @Override
                                public void onSuccess(Quizz result) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Et on démarre l'activité de résultat avec le quiz, le résultat et le score en paramètre
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("quiz", quizEnCours);
                        intent.putExtra("resultat", result);
                        intent.putExtra("score", nbBonneReponses);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    // Affiche un message si l'utilisateur n'a pas coché de bonnes réponses
                    Toast notCheckedToast = Toast.makeText(getApplicationContext(),getString(R.string.no_check_text), Toast.LENGTH_LONG );
                    notCheckedToast.show();
                }


            }
        });
    }

    /**
     * Sauvegarde les résultats de l'utilisateur
     * @param noQuASauv Le numéro de la question à sauvegarder
     */
    public void saveResultat(int noQuASauv) {
        for(int i = 0; i < 6; i++){
            if(checkBoxList.get(i).isChecked()){
                result.addReponse(noQuASauv, i);
            }
        }
    }

}
