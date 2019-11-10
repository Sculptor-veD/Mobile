package ducku.com.moneyhappy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhDangNhap extends AppCompatActivity {

    EditText txtSDT,txtPassword;
    TextView twMsg, txtdoimatkhau;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        addControls();
        addEvent();

        Toolbar tb21 = findViewById(R.id.tb21);
        setSupportActionBar(tb21);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đăng nhập");

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
