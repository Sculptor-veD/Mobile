package ducku.com.moneyhappy.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public class Preferences {
    public static final String KEY_USER = "user";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_CLIENT_ID = "client_id";

    public static void  saveUser(Context context, String userID) {
        SharedPreferences.Editor editor = context.getSharedPreferences("KEYAPP", Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER, userID);
        editor.commit();
    }
    public static void  saveToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences("KEYAPP", Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }


    public static String getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KEYAPP", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString(KEY_USER, "");
        return userID;
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KEYAPP", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_TOKEN, "");
        return token;
    }

    public static void  saveClientId(Context context, String clientId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("KEYAPP", Context.MODE_PRIVATE).edit();
        editor.putString(KEY_CLIENT_ID, clientId);
        editor.commit();
    }


    public static String getClientId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("KEYAPP", Context.MODE_PRIVATE);
        String rs = sharedPreferences.getString(KEY_CLIENT_ID, "");

        if(rs.equals("")) {
            Random rd = new Random();
            int number1 = rd.nextInt(9999);
            saveClientId(context, number1+"");
            return rs;
        }
        return rs;
    }
}
