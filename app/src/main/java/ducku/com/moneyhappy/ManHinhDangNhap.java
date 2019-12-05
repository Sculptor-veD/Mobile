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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhDangNhap extends AppCompatActivity {

    EditText txtSDT, txtPassword;
    TextView twMsg, txtdoimatkhau, txtUserId,txtAuthToken;
    Button btnDangNhap;
    CallbackManager mCallbackManager;
    LoginButton mBtnLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mCallbackManager = CallbackManager.Factory.create();
        txtUserId = (TextView) findViewById(R.id.txtSDT);
        txtAuthToken = (TextView) findViewById(R.id.txtPassword);
        mBtnLoginFacebook = (LoginButton) findViewById(R.id.login_button);
        mBtnLoginFacebook.setPermissions(Arrays.asList("public_profile","email"));
        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                txtUserId.setText("User ID: " + loginResult.getAccessToken().getUserId() );
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
        addControls();
        addEvent();

        Toolbar tb21 = findViewById(R.id.tb21);
        setSupportActionBar(tb21);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đăng nhập");
    }
    private void result(){
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON",response.getJSONObject().toString());
                        try {
                            String email = object.getString("email");
                            String name = object.getString("name");
                        }catch (JSONException e )
                        {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(ManHinhDangNhap.this, HomeActivity.class);
                        startActivity(intent);
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
