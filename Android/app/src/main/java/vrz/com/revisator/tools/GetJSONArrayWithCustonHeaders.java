package vrz.com.revisator.tools;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Victor on 22/03/2016.
 *
 * Execute une JSONArrayRequest avec un HTTPHeader personalisés
 *
 */
public class GetJSONArrayWithCustonHeaders extends JsonArrayRequest{

    private static String USER_ID = "";

    public GetJSONArrayWithCustonHeaders(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> params = new HashMap<String, String>();
        String auth = USER_ID;
        params.put("Authorization", auth);
        return params;
    }

    /**
     * Enregistre l'élément à insérer dans le header
     * @param id l'élément à insérer
     */
    public static void setUserID(String id){
        USER_ID = id;
    }

}
