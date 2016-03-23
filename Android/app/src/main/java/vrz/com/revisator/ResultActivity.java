package vrz.com.revisator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import vrz.com.revisator.objects.Quizz;
import vrz.com.revisator.objects.Resultat;

public class ResultActivity extends AppCompatActivity {

    private Quizz quizAnswered;
    private Resultat result;
    private int score;
    private TextView scoreTextView;
    private ListView resultlist;

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

        resultlist = (ListView) findViewById(R.id.resultListView);


        // TODO Afficher le résultat de l'utilisateur à chaque question et la réponse correcte à la question

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
