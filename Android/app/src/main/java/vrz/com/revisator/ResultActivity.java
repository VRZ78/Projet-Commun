package vrz.com.revisator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import vrz.com.revisator.tools.ResultAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import vrz.com.revisator.objects.Quizz;
import vrz.com.revisator.objects.Resultat;

public class ResultActivity extends AppCompatActivity {

    private Quizz quizAnswered;
    private Resultat result;
    private int score;
    private TextView scoreTextView;
    private ListView resultListView;
    private ArrayList<String[]> resultList; // 0 : QuestionTitle 1 : UserAnswer 2 : CorrectAnswer 3 : isCorrect

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Récupération du quiz et du résultat
        quizAnswered = (Quizz) getIntent().getExtras().getSerializable("quiz");
        result = (Resultat) getIntent().getExtras().getSerializable("resultat");
        score = getIntent().getExtras().getInt("score");

        // Affichage du quiz et résultat
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText(String.valueOf(score) + " / " + String.valueOf(quizAnswered.getNombreQuestion()));

        // Set up résultat ArrayList
        resultList = new ArrayList<>();

        for(int i = 0; i < quizAnswered.getNombreQuestion(); i++){
            String[] forListView = new String[5];

            forListView[0] = quizAnswered.getQuestion(i).getEnnonceQuestion();

            forListView[1] = "- ";
            for(int j = 0; j < result.getNbReponses(i); j++ ){
                if(j > 1){
                    forListView[1] = forListView[1] + "\n- ";
                }
                forListView[1] = forListView[1] + quizAnswered.getQuestion(i).getReponse(result.getReponses(i).get(j));
            }

            forListView[2] = "- ";
            for(int k = 0; k < quizAnswered.getQuestion(i).getNbBonnesReponses(); k++){
                if(k > 1){
                    forListView[2] = forListView[2] + "\n- ";
                }
                forListView[2] = forListView[2] + quizAnswered.getQuestion(i).getAllReponses()[quizAnswered.getQuestion(i).getBonneReponses()[k]];
            }

            if(forListView[1].equals(forListView[2]))
                forListView[3] = "True";
            else
                forListView[3] = "False";

            forListView[4] = String.valueOf(i + 1);

            resultList.add(forListView);

        }


        resultListView = (ListView) findViewById(R.id.resultListView);
        ListAdapter resultAdapter = new ResultAdapter(this, resultList);
        resultListView.setAdapter(resultAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
}
