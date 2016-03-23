package vrz.com.revisator.tools;

import java.util.HashMap;

import vrz.com.revisator.objects.Quizz;

/**
 * Created by Victor on 22/03/2016.
 * Interface pour gérer les callbacks lors des requêtes Volley
 */
public interface VolleyCallback {

    void onSuccess(HashMap<String, String> result);
    void onSuccess(String result);
    void onSuccess(Quizz result);


}
