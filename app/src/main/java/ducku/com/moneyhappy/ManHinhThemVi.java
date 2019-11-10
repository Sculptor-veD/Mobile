package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhThemVi extends AppCompatActivity {

    private static String beforeActivity;
    EditText editWalletName;
    EditText editWalletAmount;
    String iduser;

    public static String getBeforActivity(){
        return beforeActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_them_vi);

        Toolbar tb10 = findViewById(R.id.tb10);
        setSupportActionBar(tb10);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Thêm ví");

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        editWalletName = (EditText) findViewById(R.id.editNameWallet);
        editWalletAmount = (EditText) findViewById(R.id.editWalletAmount);
        iduser = Preferences.getUser(ManHinhThemVi.this);

        Intent intent = getIntent();
        beforeActivity =  intent.getStringExtra("activityBefore");
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menutaonhom,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuUpdateCategoy:
                func_SaveWallet(editWalletName.getText().toString(), editWalletAmount.getText().toString());
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void func_SaveWallet(String nameWallet, String amount){
        if(nameWallet.isEmpty()) {
            Toast.makeText(ManHinhThemVi.this, "Nhập tên ví", Toast.LENGTH_SHORT).show();
        }
        else if(amount.isEmpty()) {
            Toast.makeText(ManHinhThemVi.this, "Nhập số tiền!", Toast.LENGTH_SHORT).show();
        }
        else{
            String url = "act=save_wallet&wallet_name="+nameWallet+"&wallet_amount="+amount+"&iduser="+iduser;
            url=url.replace(" ", "%20");
            new SaveWallet().execute(url);
        }
    }

    public class SaveWallet extends api {
        @Override
        protected void onPostExecute(String s) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                String result = obj.getString("result");
                if(result.equals("true")){
                    Toast.makeText(ManHinhThemVi.this, "Save Success!", Toast.LENGTH_SHORT).show();
                    String bfActivity = ManHinhThemVi.getBeforActivity();
                    if(bfActivity.equals("ManHinhTaoVi")) {
                        Intent intent = new Intent(ManHinhThemVi.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    if(bfActivity.equals("ManHinhVi")) {
                        onBackPressed();
                    }
                } else {
                    Toast.makeText(ManHinhThemVi.this, "Save Error!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
