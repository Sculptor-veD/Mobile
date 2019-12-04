package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import ducku.com.moneyhappy.adapter.IconAdapter;

public class ManHinhLoadIcon extends AppCompatActivity {

    GridView gridView;
    ArrayList<Integer> dshinh;
    IconAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_load_icon);

        Toolbar tb15 = findViewById(R.id.tb15);
        setSupportActionBar(tb15);
        setTitle("Chọn hình");

        addControls();
        addEvents();
    }

    private void addEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=getIntent();
                intent.putExtra("id_hinh",dshinh.get(position).intValue());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void addControls() {
        gridView=findViewById(R.id.gvhinh);
        dshinh=new ArrayList<>();
       dshinh.add(R.drawable.medal);dshinh.add(R.drawable.football);dshinh.add(R.drawable.crowns);
        dshinh.add(R.drawable.balance);dshinh.add(R.drawable.weddingcouple);dshinh.add(R.drawable.cake);
        dshinh.add(R.drawable.cake1);dshinh.add(R.drawable.pumpkin);dshinh.add(R.drawable.birthday);
       dshinh.add(R.drawable.gifts);dshinh.add(R.drawable.cocktails);dshinh.add(R.drawable.beer);
        dshinh.add(R.drawable.beer1);dshinh.add(R.drawable.heartbroken);dshinh.add(R.drawable.hotel1);
        dshinh.add(R.drawable.hotel);dshinh.add(R.drawable.billiard);dshinh.add(R.drawable.billiards);
       dshinh.add(R.drawable.poker);dshinh.add(R.drawable.dice);dshinh.add(R.drawable.report);
        dshinh.add(R.drawable.hospital);dshinh.add(R.drawable.hospital1);

        dshinh.add(R.drawable.piggybank);dshinh.add(R.drawable.baker);dshinh.add(R.drawable.settings);
        dshinh.add(R.drawable.cart);dshinh.add(R.drawable.salary);dshinh.add(R.drawable.diet);
        dshinh.add(R.drawable.hat);dshinh.add(R.drawable.santaclaus);dshinh.add(R.drawable.father);

        dshinh.add(R.drawable.friendship);dshinh.add(R.drawable.hearts);dshinh.add(R.drawable.heart);
        dshinh.add(R.drawable.dog);dshinh.add(R.drawable.dog1);dshinh.add(R.drawable.buildings);
        dshinh.add(R.drawable.house);dshinh.add(R.drawable.shop1);dshinh.add(R.drawable.shop);
        dshinh.add(R.drawable.hawaii);dshinh.add(R.drawable.eiffeltower);dshinh.add(R.drawable.statueofliberty);
        dshinh.add(R.drawable.suitcase1);dshinh.add(R.drawable.backpack);dshinh.add(R.drawable.safebox);
        dshinh.add(R.drawable.aeroplane);dshinh.add(R.drawable.airfreight);dshinh.add(R.drawable.exercise);
        dshinh.add(R.drawable.gym);dshinh.add(R.drawable.woman);dshinh.add(R.drawable.muscle);
        dshinh.add(R.drawable.medicine);dshinh.add(R.drawable.pills);dshinh.add(R.drawable.policecar);
        dshinh.add(R.drawable.policeman);dshinh.add(R.drawable.taxi);dshinh.add(R.drawable.bus1);
        dshinh.add(R.drawable.bus);dshinh.add(R.drawable.bank);dshinh.add(R.drawable.debitcard);

        adapter = new IconAdapter(ManHinhLoadIcon.this,R.layout.custom_gv,dshinh);

        gridView.setAdapter(adapter);

    }
}
