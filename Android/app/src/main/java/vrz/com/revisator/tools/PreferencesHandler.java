package vrz.com.revisator.tools;

import android.content.Context;
import android.content.SharedPreferences;

import vrz.com.revisator.R;

/**
 * Created by Victor on 18/01/2016.
 */
public class PreferencesHandler {

    /**
     * Vérifie si des logs utilsateurs sont déjà enregistrés ou non
     * @param context getApplicationContext()
     * @return
     */
    public static boolean areLoginInfoStored(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
        boolean hasLog = sharedPref.getBoolean("hasLogInfo", false);

        if(hasLog == false)
            return false;
        else
            return true;

    }

    /**
     * Sauvegarde les logs de l'utilisateur
     * @param username Le nom d'utilisateur
     * @param password Le mot de passe
     * @param userID L'ID de l'utilisateur, renvoyé par le serveur lors de la connexion
     * @param context getApplicationContext()
     */
    public static void saveCredential(String username, String password, String userID, Context context){
        SharedPreferences shareP = context.getSharedPreferences(context.getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePrefEditor = shareP.edit();
        sharePrefEditor.putBoolean("hasLogInfo", true);
        sharePrefEditor.putString("username", username);
        sharePrefEditor.putString("password", password);
        sharePrefEditor.putString("ID", userID);
        sharePrefEditor.commit();
    }

    public static void saveServerIP(String ip, Context context){
        SharedPreferences shareP = context.getSharedPreferences(context.getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePrefEditor = shareP.edit();
        sharePrefEditor.putString("ip", ip);
        sharePrefEditor.commit();
    }

    /**
     * Supprime les login de la mémoire
     * @param context getApplicationContext()
     */
    public static void deleteCredential(Context context){
        SharedPreferences shareP = context.getSharedPreferences(context.getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharePrefEditor = shareP.edit();
        sharePrefEditor.putBoolean("hasLogInfo", false);
        sharePrefEditor.remove("username");
        sharePrefEditor.remove("password");
        sharePrefEditor.commit();

    }

    /**
     * Retourne le nom d'utilisateur stocké dans l'application
     * @param context getApplicationContext()
     * @return Le nom d'utilisateur stocké
     */
    public static String getUsername(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        return username;
    }

    /**
     * Retourne l'IP du serveur
     * @param context getApplicationContext()
     * @return l'IP du serveur
     */
    public static String getServerIP(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.pref_userinfo), Context.MODE_PRIVATE);
        String ip = sharedPref.getString("ip", "");
        return ip;
    }


}
