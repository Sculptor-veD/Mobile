package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ducku.com.moneyhappy.adapter.WalletAdapter;
import ducku.com.moneyhappy.model.Preferences;
import ducku.com.moneyhappy.model.Wallet;

public class ManHinhVi extends AppCompatActivity {


    ImageView imgthem;
    TextView txtTong;
    ListView lvWallet;
    ArrayList<Wallet> arrayWallet;
    WalletAdapter adapter;
    Resources res;
    String userID;

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_vi);


        Toolbar tb3= findViewById(R.id.tb3);
        setSupportActionBar(tb3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chọn ví");

        addControls();
        addEvent();

        new GetWallet().execute("act=loadwallet&iduser="+userID);
    }

    private void addControls() {
        userID = Preferences.getUser(this);
        lvWallet = (ListView) findViewById(R.id.lvwl);
        arrayWallet = new ArrayList<>();
        res = getResources();

        imgthem=findViewById(R.id.imgThemVi);
        txtTong=findViewById(R.id.txtTong);
    }

    private  void addEvent()
    {
        lvWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(ManHinhVi.this,ManHinhNhom.class);
                intent.putExtra("IDWallet",arrayWallet.get(position).get_id());
                intent.putExtra("NameWallet",arrayWallet.get(position).get_name());
                startActivity(intent);
            }
        });

        imgthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhVi.this,ManHinhThemVi.class);
                intent.putExtra("activityBefore", "ManHinhVi");
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menuvi,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menusua:
                Intent intent1 = new Intent(ManHinhVi.this,ManHinhHienThiVi.class);
                startActivity(intent1);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetWallet extends api {
        @Override
        protected void onPostExecute(String s) {

            try {
                JSONArray array = new JSONArray(s);

                for (int i = 0 ; i < array.length(); i++) {
                    int tong=0;
                    JSONObject wallet = array.getJSONObject(i);
                    int id = wallet.getInt("id");
                    int amount = wallet.getInt("amount");
                    String name =wallet.getString("name");
                    int idImg = res.getIdentifier(wallet.getString("image_name") , "drawable", getPackageName());
                    arrayWallet.add(new Wallet(id, amount, idImg, name));

                }

                adapter = new WalletAdapter(ManHinhVi.this, R.layout.custom_listview_wallet, arrayWallet);
                lvWallet.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
