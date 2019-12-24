package ducku.com.moneyhappy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.Profile;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhHoSo extends AppCompatActivity {

     ProfilePictureView profilePictureView;
     TextView textView,textView1,textView2,textView3;
     EditText txtemail,txtphone,txtusername;
     String userID;
     ImageView imageView1,imageView2,imageView3;
     Button button;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ho_so);
        profilePictureView = (ProfilePictureView) findViewById(R.id.imagefb);
        textView = (TextView) findViewById(R.id.namefb);
        textView1 = (TextView) findViewById(R.id.tienchi);
        textView2 = (TextView) findViewById(R.id.tongtien);
        textView3 = (TextView) findViewById(R.id.tienthu);
        txtemail = findViewById(R.id.txtemail);
        txtphone = findViewById(R.id.txtphone);
        imageView1 = findViewById(R.id.fix1);
        imageView2 = findViewById(R.id.fix2);
        imageView3 = findViewById(R.id.fix3);
        txtusername = findViewById(R.id.txtusername);
        button = findViewById(R.id.buttonsave);
        userID =Preferences.getUser(ManHinhHoSo.this);
        Log.e("JSON",userID);
        getFBInfo();
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               txtemail.setEnabled(true);
               txtphone.setEnabled(false);
               txtusername.setEnabled(false);
               button.setVisibility(View.VISIBLE);
               button.setClickable(true);
            }

        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtemail.setEnabled(false);
                txtphone.setEnabled(true);
                txtusername.setEnabled(false);
                button.setVisibility(View.VISIBLE);
                button.setClickable(true);
            }

        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtemail.setEnabled(false);
                txtphone.setEnabled(false);
                txtusername.setEnabled(true);
                button.setVisibility(View.VISIBLE);
                button.setClickable(true);
            }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(ManHinhHoSo.this,"OK",Toast.LENGTH_LONG).show();
                new UpdateHoSo().execute("act=update_account&email="+txtemail.getText().toString()+"&phone="+txtphone.getText().toString()+"&name="+txtusername.getText().toString()+"&userid="+userID);
                recreate();
            }


        });

    }
    void getFBInfo(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String data = prefs.getString("fbid", "noid");
        String fbname = prefs.getString("fbname","Unknown");
        profilePictureView.setProfileId(data);
        textView.setText(fbname);
        new LoadThuChi().execute("act=mtotal&userid="+userID);
        new GetHoSo().execute("act=loadaccount&iduser="+userID);

    }

    private class GetHoSo extends api{
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);

                txtemail.setText(jsonObject.getString("email").toString());
                    txtphone.setText(jsonObject.getString("phone").toString());
                    txtusername.setText(jsonObject.getString("name").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class UpdateHoSo extends api{
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                if (jsonObject.getString("result").equals("true"))
                    Toast.makeText(ManHinhHoSo.this,"Update Success",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ManHinhHoSo.this,"Update Fail",Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class LoadThuChi extends api{
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                String total = jsonObject.getString("TotalMoney");
               // textView1.setText(jsonObject.getString("TotalSpend").toString());
                textView1.setText(jsonObject.getString("TotalSpend").toString());
                textView3.setText(jsonObject.getString("TotalCollection").toString());
                textView2.setText(jsonObject.getString("TotalMoney").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
