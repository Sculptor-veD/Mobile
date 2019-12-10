package ducku.com.moneyhappy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhDangNhap extends AppCompatActivity {
    EditText txtSDT, txtPassword;
    TextView twMsg, txtdoimatkhau, txtUserId, txtAuthToken;
    Button btnDangNhap;
    CallbackManager mCallbackManager;
    LoginButton mBtnLoginFacebook;
    static GoogleSignInClient mGoogleSignInClient;
    String getIDFB;
    AccessTokenTracker accessTokenTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mCallbackManager = CallbackManager.Factory.create();
        txtUserId = (TextView) findViewById(R.id.txtSDT);
        txtAuthToken = (TextView) findViewById(R.id.txtPassword);
        mBtnLoginFacebook = (LoginButton) findViewById(R.id.login_button);

        loginFB();
        loginGoogle();
        addControls();
        addEvent();

        Toolbar tb21 = findViewById(R.id.tb21);
        setSupportActionBar(tb21);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đăng nhập");
    }

    void loginFB() {
        mBtnLoginFacebook.setPermissions(Arrays.asList("public_profile", "email"));
        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                result();
            }

            @Override
            public void onCancel() {
                txtUserId.setText("Login canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                txtUserId.setText("Login failed.");
            }

        });

            }



    private void result(){
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON",response.getJSONObject().toString());
                        try {
                             getIDFB = object.getString("id");
                                Toast.makeText(ManHinhDangNhap.this,getIDFB,Toast.LENGTH_LONG);
                               // if(getIDFB.length() > 0)
                                     new LoginMXHFB().execute("act=load_socialnetwork&socialnetwork_name=facebook&socialnetwork_id="+getIDFB);


                        }catch (JSONException e )
                        {
                            e.printStackTrace();
                        }
                        /*Intent intent = new Intent(ManHinhDangNhap.this, HomeActivity.class);
                        startActivity(intent);*/
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
           // txtUserId.setText(account.getId().toString());
            String getID = account.getId().toString();
            new LoginMXH().execute("act=load_socialnetwork&socialnetwork_name=google&socialnetwork_id="+getID);
            if(txtPassword.getText().toString().equals("***"))
            {
             new SigninMXH().execute("act=new_socialnetwork&socialnetwork_name=google&socialnetwork_id="+getID+"&user_id=new_user");
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Success", "signInResult:failed code=" + e.getStatusCode());
        }
    }
     void loginGoogle(){
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.gglogin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 001);
            }
        });
        /* findViewById(R.id.log_button).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mGoogleSignInClient.signOut();
             }
         });*/
     }

    private void addEvent() {

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempSDT      = txtSDT.getText().toString();
                String tempPassword = txtPassword.getText().toString();
                new GoiDangNhap().execute("act=login&phone="+tempSDT+"&password="+tempPassword);
            }
        });
        txtdoimatkhau.setOnClickListener(new View.OnClickListener() {
        @Override
         public void onClick(View v) {
         Intent intent = new Intent(ManHinhDangNhap.this, ManHinhDangKy.class);
         intent.putExtra("action","ForGotPassword");
         startActivity(intent);
         }
        });

    }
    public static void logoutgg(){
        mGoogleSignInClient.signOut();
    }
    private void addControls() {
        txtSDT=findViewById(R.id.txtSDT);

        txtSDT.setSelection(0);
        txtPassword=findViewById(R.id.txtPassword);
        txtSDT.setText("");
        txtPassword.setText("");

        twMsg = findViewById(R.id.textView2);
        txtdoimatkhau= findViewById(R.id.txtQuenmatkhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);


    }
    private class SigninMXH extends api {
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                if(jsonObject.getString("result").equals("true"))
                {
                    Intent intent = new Intent(ManHinhDangNhap.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class LoginMXH extends  api{
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                String jsonid  = jsonObject.getString("user_id");
                if(jsonid.equals("0"))
                {
                    txtPassword.setText("***");
                }
                else{
                    txtSDT.setText("Login Success with ID" + jsonid);
                    Preferences.saveUser(ManHinhDangNhap.this, jsonid);

                    Intent intent = new Intent(ManHinhDangNhap.this, HomeActivity.class);
                    startActivity(intent);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class LoginMXHFB extends  api{
        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                String jsonid  = jsonObject.getString("user_id");
                if(jsonid.equals("0"))
                {
                    txtPassword.setText("***");
                    new SigninMXH().execute("act=new_socialnetwork&socialnetwork_name=facebook&socialnetwork_id="+getIDFB+"&user_id=new_user");
                }
                else{
                    txtSDT.setText("Login Success with ID" + jsonid);
                    Preferences.saveUser(ManHinhDangNhap.this, jsonid);
                    Intent intent = new Intent(ManHinhDangNhap.this, HomeActivity.class);
                    startActivity(intent);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class GoiDangNhap extends api {
        @Override
        protected void onPostExecute(String s) {

            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                String accountId = obj.getString("account_id");
                String checkWallet = obj.getString("wallet");
                if(accountId.equals("false")){
                    //bla bla
                    twMsg.setText("Thong tin khong chinh xac");
                } else {
                    //bla bla
                    twMsg.setText("Dang nhap thanh cong");
                    Preferences.saveUser(ManHinhDangNhap.this, accountId);
                    Intent intent;

                    if(checkWallet.equals("true")) {
                        intent=new Intent(ManHinhDangNhap.this, HomeActivity.class);
                    }
                    else {
                        intent=new Intent(ManHinhDangNhap.this, ManHinhTaoVi.class);
                    }
                    startActivity(intent);
                }
                //xoa pass
                txtPassword.setText("");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
