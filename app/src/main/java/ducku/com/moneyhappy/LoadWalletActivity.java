package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ducku.com.moneyhappy.adapter.WalletAdapter;
import ducku.com.moneyhappy.model.Preferences;
import ducku.com.moneyhappy.model.Wallet;

public class LoadWalletActivity extends AppCompatActivity {

    ListView lvWallet;
    ArrayList<Wallet> arrayWallet;
    WalletAdapter adapter;
    Resources res;

    String urlAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_wallet);

        addControls();
        addEvent();
        Toolbar tb20 = findViewById(R.id.tb20);
        setSupportActionBar(tb20);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Danh sách ví");

        new GetWallet().execute(urlAPI);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu1:
                //code xử lý khi bấm menu1
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        lvWallet = (ListView) findViewById(R.id.lv_wallet);
        arrayWallet = new ArrayList<>();
        res = getResources();

        String userID = Preferences.getUser(LoadWalletActivity.this);
        urlAPI = "act=loadwallet&iduser="+userID;

    }

    private void addEvent() {

        lvWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("id_wallet", arrayWallet.get(position).get_id());
                intent.putExtra("name_wallet", arrayWallet.get(position).get_name());
                intent.putExtra("image_wallet", arrayWallet.get(position).get_img());
                setResult(Activity.RESULT_OK,intent);;
                finish();
            }
        });
    }

    private class GetWallet extends api {
        @Override
        protected void onPostExecute(String s) {

            try {
                JSONArray array = new JSONArray(s);

                for (int i = 0 ; i < array.length(); i++) {
                    JSONObject wallet = array.getJSONObject(i);
                    int id = wallet.getInt("id");
                    int amount = wallet.getInt("amount");
                    String name =wallet.getString("name");
                    int idImg = res.getIdentifier(wallet.getString("image_name") , "drawable", getPackageName());
                    arrayWallet.add(new Wallet(id, amount, idImg, name));
                }

                adapter = new WalletAdapter(LoadWalletActivity.this, R.layout.custom_listview_wallet, arrayWallet);
                lvWallet.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
