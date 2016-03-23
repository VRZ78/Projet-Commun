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

import org.json.JSONException;

import java.util.HashMap;

import vrz.com.revisator.objects.Quizz;
import vrz.com.revisator.tools.GetJSONArrayWithCustonHeaders;
import vrz.com.revisator.tools.PostJSONArray;
import vrz.com.revisator.tools.PreferencesHandler;
import vrz.com.revisator.tools.VolleyCallback;
import vrz.com.revisator.tools.WebApiHandler;

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
        existingCredentialCheck();

        // Listener Se Connecter
        loginButton.setOnClickListener(loginButtonListener);

        // Listener Créer un compte
        createAccountButton.setOnClickListener(createAccountButtonListener);

        // Listener Mot de passe oublié
        forgotPasswordButton.setOnClickListener(forgotPasswordButtonListener);
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
    public void activitySwitcher(Class activityName){
        Intent leIntent = new Intent (getApplicationContext(), activityName);
        startActivity(leIntent);
    }

    /**
     * Vérifie si l'utilisateur s'est déjà connecté
     */
    public void existingCredentialCheck(){
        boolean hasLog = PreferencesHandler.areLoginInfoStored(getApplicationContext());
        // Si c'est le cas, on se reconnecte automatiquement
        if(hasLog == true){
            // Récupération des crédentiels
            SharedPreferences shareP = getSharedPreferences(getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
            final String username = shareP.getString("username", null);
            final String password = shareP.getString("password", null);

            try {
                WebApiHandler.connection(username, password, getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(HashMap<String, String> result) {
                    }

                    @Override
                    public void onSuccess(String result) {
                        // Si la connexion réussie, stock les login et mdp via SharedPreference
                        PreferencesHandler.saveCredential(username, password, result, getApplicationContext());
                        PostJSONArray.setUserID(result);
                        GetJSONArrayWithCustonHeaders.setUserID(result);
                        // Lance le menu principal
                        activitySwitcher(MainMenuActivity.class);

                        // Arrête cette 'activité
                        finish();
                    }

                    @Override
                    public void onSuccess(Quizz result) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * OnClickListener pour le bouton Login
     */
    View.OnClickListener loginButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            // Récupère ce que l'utilisateur a entré dans l'EditText
            final String username = userNameET.getText().toString().trim();
            final String password = passwordET.getText().toString().trim();


            // Si l'utilisateur n'a rien saisi, on l'avertit
            if(username.equals("") || password.equals("")){
                Toast noImputToast = Toast.makeText(getApplicationContext(),getString(R.string.no_imput_toast), Toast.LENGTH_LONG );
                noImputToast.show();
            }
            // Sinon, passe les infos au serveur et on se connecte
            else {
                try {
                    WebApiHandler.connection(username, password, getApplicationContext(), new VolleyCallback() {
                        @Override
                        public void onSuccess(HashMap<String, String> result) {
                        }

                        @Override
                        public void onSuccess(String result) {
                            // Si la connexion réussie, stock les login et mdp via SharedPreference
                            PreferencesHandler.saveCredential(username, password, result, getApplicationContext());
                            PostJSONArray.setUserID(result);
                            GetJSONArrayWithCustonHeaders.setUserID(result);
                            // Lance le menu principal
                            activitySwitcher(MainMenuActivity.class);

                            // Arrête cette 'activité
                            finish();
                        }

                        @Override
                        public void onSuccess(Quizz result) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    View.OnClickListener createAccountButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activitySwitcher(CreateAccountActivity.class);
        }
    };

    View.OnClickListener forgotPasswordButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activitySwitcher(ForgotPasswordActivity.class);
        }
    };

}
