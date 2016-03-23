package vrz.com.revisator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import vrz.com.revisator.tools.PreferencesHandler;
import vrz.com.revisator.tools.VolleyCallback;
import vrz.com.revisator.tools.WebApiHandler;

public class MainMenuActivity extends AppCompatActivity {

    private Button onePlayerButton;
    private Button twoPlayerButton;
    private Button friendsButton;
    private Button statisticButton;
    private TextView usernameDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // initialie le TextView avec "Bienvenue"
        usernameDisplay = (TextView) findViewById(R.id.textView);
        // Récupère le username dans SharePreference
        String username = " " + PreferencesHandler.getUsername(getApplicationContext());
        // Display le username
        usernameDisplay.setText(getString(R.string.welcome)+username);
        // Initialise les boutons
        onePlayerButton = (Button) findViewById(R.id.OnePlayerButton);
        onePlayerButton.setOnClickListener(onePlayerButtonListener);
        twoPlayerButton = (Button) findViewById(R.id.MultiplayerButton);
        twoPlayerButton.setOnClickListener(twoPlayerButtonListener);
        friendsButton = (Button) findViewById(R.id.FriendlistButton);
        statisticButton = (Button) findViewById(R.id.StatButton);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
        // Lors de l'appui sur le bouton Se Déconnecter
        else if(id == R.id.disconnectButton) {

            // Supprime les credential de la mémoire
            PreferencesHandler.deleteCredential(getApplicationContext());

            // Relance l'écran de login
            Intent disconnectIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(disconnectIntent);
    
            // Termine l'activité
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onePlayerButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SelectionActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener twoPlayerButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };



}
