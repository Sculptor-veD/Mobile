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
        dshinh.add(R.drawable.chi_phone);dshinh.add(R.drawable.chi_wine);dshinh.add(R.drawable.chi_bank);
        dshinh.add(R.drawable.chi_browser);dshinh.add(R.drawable.chi_bus);dshinh.add(R.drawable.chi_conversation);
        dshinh.add(R.drawable.chi_fries);dshinh.add(R.drawable.chi_sick);dshinh.add(R.drawable.chi_taxi);
        dshinh.add(R.drawable.thu_boss);
        dshinh.add(R.drawable.thu_satute);
        dshinh.add(R.drawable.thu_map);
        dshinh.add(R.drawable.thu_palm);

        adapter = new IconAdapter(ManHinhLoadIcon.this,R.layout.custom_gv,dshinh);

        gridView.setAdapter(adapter);

    }
}
