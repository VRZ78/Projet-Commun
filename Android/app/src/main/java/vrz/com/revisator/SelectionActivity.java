package vrz.com.revisator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.HashMap;

import vrz.com.revisator.objects.Quizz;
import vrz.com.revisator.tools.VolleyCallback;
import vrz.com.revisator.tools.WebApiHandler;

public class SelectionActivity extends AppCompatActivity {

    private ListView subjectsOrQuizzes;
    private HashMap<String, String> idMatch; // <ID du quiz>, <Titre du quiz>
    private String[] subjectsOrQuizzesContainer; // Elements de la ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        this.setTitle(R.string.title_activity_selection_course);

        // Récupération de la liste des matières
        WebApiHandler.getMatieres(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> result) {
                idMatch = result;

                // Mise en place éléments du ListView
                subjectsOrQuizzesContainer = new String[result.size()];
                // Extraction des titres des quiz pour les insérer dans le container de la ListView
                result.keySet().toArray(subjectsOrQuizzesContainer);
                // Appelle de la méthode créant la ListView, avec false car on devra recharger avec les quiz
                setArray(false);
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccess(Quizz result) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection, menu);
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

    /**
     * Recharge la page avec une liste de quiz
     * @param nomMatiere Le nom de la matière pour laquelle on veut des quiz
     */
    public void reloadWithQuizzes(String nomMatiere) {
        this.setTitle(R.string.title_activity_selection_quiz);
        // Récupération de l'id de la matière
        String idMatiere = idMatch.get(nomMatiere);
        // Récupération du quiz
        WebApiHandler.getQuizzez(idMatiere, getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(HashMap<String, String> result) {
                // Pareil qu'au dessus
                idMatch = result;
                subjectsOrQuizzesContainer = new String[result.size()];
                result.keySet().toArray(subjectsOrQuizzesContainer);
                setArray(true);
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onSuccess(Quizz result) {

            }
        });
    }

    /**
     * Crée et affiche la ListView contenant les quiz ou les matières
     * @param isLast true si on affiche des quiz, faux si on affiche des matières
     */
    public void setArray(boolean isLast) {
        // Création de la ListView
        ListAdapter subjectsOrQuizzesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectsOrQuizzesContainer);
        subjectsOrQuizzes = (ListView) findViewById(R.id.subjectsOrQuizzesView);
        subjectsOrQuizzes.setAdapter(subjectsOrQuizzesAdapter);
        if(!isLast) {
            // Si on affiche des matières, on recharge la page avec des quiz lors de la sélection d'une matière
            subjectsOrQuizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    reloadWithQuizzes(subjectsOrQuizzes.getItemAtPosition(position).toString());
                }
            });
        }
        else{
            // Si on affiche des quiz, on démarre QuizActivity
            subjectsOrQuizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    // On passe l'ID du quiz à QuizActivity
                    intent.putExtra("quizId", idMatch.get(subjectsOrQuizzes.getItemAtPosition(position).toString()));
                    startActivity(intent);
                }
            });
        }
    }


}
