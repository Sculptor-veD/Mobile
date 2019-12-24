package ducku.com.moneyhappy.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            saveClientId(context, md5(ts)+"");
            return rs;
        }
        return rs;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
