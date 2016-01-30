package vrz.com.revisator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vrz.com.revisator.tools.PreferencesHandler;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameET;
    private EditText passwordET;
    private Button loginButton;
    private Button createAccountButton;
    private Button forgotPasswordButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Initilisation des éléments de l'UI
        userNameET = (EditText) findViewById(R.id.userNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginButton = (Button) findViewById(R.id.connectButton);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordbutton);


        // Vérifie si l'utilisateur s'est déjà connecté
        boolean hasLog = PreferencesHandler.areLoginInfoStored(getApplicationContext());
        // Si c'est le cas, on se reconnecte automatiquement
        if(hasLog == true){
            // Récupération des crédentiels
            SharedPreferences shareP = getSharedPreferences(getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
            String username = shareP.getString("username", null);
            String password = shareP.getString("password", null);

            // TODO Envoyer les info au serveur
            // Lance le menu principal
            ActivitySwitcher(MainMenuActivity.class);
            // Déttruit cette activité
            finish();

        }

        // Listener Se Connecter
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupère ce que l'utilisateur a entré dans l'EditText
                String username = userNameET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                // Si l'utilisateur n'a rien saisi, on l'avertit
                if(username.equals("") || password.equals("")){
                    Toast noImputToast = Toast.makeText(getApplicationContext(),getString(R.string.no_imput_toast), Toast.LENGTH_LONG );
                    noImputToast.show();
                }
                // Sinon, passe les infos au serveur et on se connecte
                else {
                    // TODO Passer les informations au server (via appel à classe unique)

                    // Si la connexion réussie, stock les login et mdp via SharedPreference
                    PreferencesHandler.saveCredential(username, password, getApplicationContext());

                    // Lance le menu principal
                    ActivitySwitcher(MainMenuActivity.class);

                    // Arrête cette 'activité
                    finish();
                }
            }
        });

        // Listener Créer un compte
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySwitcher(CreateAccountActivity.class);
            }
        });

        // Listener Mot de passe oublié
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySwitcher(ForgotPasswordActivity.class);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    // Cache le bouton Settings
   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
       MenuItem item= menu.findItem(R.id.action_settings);
       item.setVisible(false);
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

    // Permet de lancer une autre activité
    public void ActivitySwitcher(Class activityName){
        Intent leIntent = new Intent (getApplicationContext(), activityName);
        startActivity(leIntent);
    }



}
