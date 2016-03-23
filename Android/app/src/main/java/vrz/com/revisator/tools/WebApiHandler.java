package vrz.com.revisator.tools;

import android.content.Context;
import android.media.MediaRouter;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import vrz.com.revisator.objects.Question;
import vrz.com.revisator.objects.Quizz;

/**
 * Created by Victor on 04/02/2016.
 * Classe unique pour les appel à l'API
 */
public class WebApiHandler {

    public static final String SERVER_ADRESS = "http://192.168.1.72:8080/";

    /**
     * Retourne la liste des établissements
     * @param context
     * @param callback Méthode à éxecuter en cas de succès de la requête
     */
    public static void getEtablissements(Context context, final VolleyCallback callback) {
        RequestQueue rQueue = Volley.newRequestQueue(context);
        String url = WebApiHandler.SERVER_ADRESS+"listeMatiereProf";
        JsonArrayRequest getEtablissements = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String[] etablissements = new String[response.length()];
                for(int i = 0; i < response.length(); i++){
                    try {
                        etablissements[i] = response.getJSONObject(i).getString("nom");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               // callback.onSuccess(etablissements);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(getEtablissements);


    }

    /**
     * Permet de se connecter à l'application
     * @param username Le pseudo à utiliser pour se connecter
     * @param password Le mot de passe à utiliser pour se connecter
     * @param context
     * @param callback Méthode à éxecuter en cas de succès de la requête
     * @throws JSONException
     */
    public static void connection(String username, String password, Context context, final VolleyCallback callback) throws JSONException {

        JSONObject credential = new JSONObject();
        credential.put("username", username);
        credential.put("password", password);
        RequestQueue rQueue = Volley.newRequestQueue(context);
        String url = WebApiHandler.SERVER_ADRESS+"connexion";
        PostJSONArray connect = new PostJSONArray(Request.Method.POST, url, credential, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String id = "";
                try {
                    id = response.getJSONObject(0).getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(id);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(connect);

    }

    /**
     * Retourne la liste des matières
     * @param context
     * @param callback Méthode à éxecuter en cas de succès de la requête
     */
    public static void getMatieres(Context context, final VolleyCallback callback){

        RequestQueue rQueue = Volley.newRequestQueue(context);
        String url = WebApiHandler.SERVER_ADRESS+"listeMatieres";
        GetJSONArrayWithCustonHeaders getMatieres = new GetJSONArrayWithCustonHeaders(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                HashMap<String, String> matieres = new HashMap<String, String>();
                for(int i = 0; i < response.length(); i++){
                    try {
                        matieres.put(response.getJSONObject(i).getString("nom"), response.getJSONObject(i).getString("idMatiere"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(matieres);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(getMatieres);

    }

    /**
     * Retourne la liste des quiz liés à une matière
     * @param idMatiere l'ID de la matière pour laquelle on soihaite récupérer la liste des quiz
     * @param context
     * @param callback Méthode à éxecuter en cas de succès de la requête
     */
    public static void getQuizzez(String idMatiere, Context context, final VolleyCallback callback){

        RequestQueue rQueue = Volley.newRequestQueue(context);
        String url = WebApiHandler.SERVER_ADRESS+"listeQuizz/" + idMatiere;
        GetJSONArrayWithCustonHeaders getQuizzez = new GetJSONArrayWithCustonHeaders(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                HashMap<String, String> quizzez = new HashMap<String, String>();
                for(int i = 0; i < response.length(); i++){
                    try {
                        quizzez.put(response.getJSONObject(i).getString("nom"), response.getJSONObject(i).getString("idQuizz"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(quizzez);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(getQuizzez);

    }

    /**
     * Retourne un quiz grâce à son id
     * @param idQuiz l'ID du quiz que l'on souhaite récupérer
     * @param context
     * @param callback Méthode à éxecuter en cas de succès de la requête
     */
    public static void getQuiz(String idQuiz, Context context, final VolleyCallback callback){

        RequestQueue rQueue = Volley.newRequestQueue(context);
        String url = WebApiHandler.SERVER_ADRESS+"quizz/" + idQuiz;
        GetJSONArrayWithCustonHeaders getQuiz = new GetJSONArrayWithCustonHeaders(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Construction de l'objet Quiz à partir de la réponse
                Quizz quiz = new Quizz(1);
                int noQu = 0;
                int noRep = 0;
                // Pour chaque item du tableau
                for(int i = 0; i < response.length(); i++){
                    // Si c'est le premier
                    if(i == 0){
                        try {
                            // Ajout de la première question
                            quiz.addQuestion(new Question(response.getJSONObject(i).getString("nom")));
                            // Ajout de la première réponse
                            quiz.getQuestion(noQu).addReponse(response.getJSONObject(i).getString("proposition"));
                            if(response.getJSONObject(i).getInt("estValide") == 1){
                                quiz.getQuestion(noQu).addBonneReponse(noRep);
                            }
                            noRep++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // Pour tout les autres
                    else{
                        try {
                            // Si le nom de la question est différent
                            if(!response.getJSONObject(i-1).getString("nom").equals(response.getJSONObject(i).getString("nom"))){
                                // On insère une nouvelle question
                                quiz.addQuestion(new Question(response.getJSONObject(i).getString("nom")));
                                noQu++;
                                // On remet le num de réponse à 1
                                noRep = 0;
                            }
                            // Ajout de la réponse
                            quiz.getQuestion(noQu).addReponse(response.getJSONObject(i).getString("proposition"));
                            if(response.getJSONObject(i).getInt("estValide") == 1){
                                quiz.getQuestion(noQu).addBonneReponse(noRep);
                            }
                            noRep++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                callback.onSuccess(quiz);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(getQuiz);

    }

    /**
     * Envoir le score de l'utilisateur à un quiz
     * @param nbBonnesReponses Le nombre de bonnes réponses de l'utilisateur au quiz
     * @param nbReponses Le nombre de questions du quiz
     * @param idQuiz L'id du quiz
     * @param context
     * @param callback Méthode à éxecuter en cas de succès de la requête
     * @throws JSONException
     */
    public static void sendResult(String nbBonnesReponses, String nbReponses, String idQuiz, Context context, final VolleyCallback callback) throws JSONException {

        JSONObject result = new JSONObject();
        result.put("bonneReponse", nbBonnesReponses);
        result.put("total", nbReponses);
        result.put("idQuizz", idQuiz);

        RequestQueue rQueue = Volley.newRequestQueue(context);
        String url = WebApiHandler.SERVER_ADRESS+"resultats";
        PostJSONArray sendResult = new PostJSONArray(Request.Method.POST, url, result, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String state = "Success";
                callback.onSuccess(state);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        rQueue.add(sendResult);

    }







}
