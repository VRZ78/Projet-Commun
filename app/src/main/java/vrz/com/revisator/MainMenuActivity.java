package vrz.com.revisator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import vrz.com.revisator.tools.PreferencesHandler;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // initialie le TextView avec "Bienvenue"
        TextView usernameDisplay = (TextView) findViewById(R.id.textView);
        // Récupère le username dans SharePreference
        String username = PreferencesHandler.getUsername(getApplicationContext());
        // Display le username
        usernameDisplay.setText(getString(R.string.welcome)+username);
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


}
