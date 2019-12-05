package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ducku.com.moneyhappy.adapter.PageTransactionAdapter;
import ducku.com.moneyhappy.adapter.TransactionAdapter;
import ducku.com.moneyhappy.model.Transaction;

public class HomeActivity extends AppCompatActivity {
    private final int NUM_TAB = 12;
    private List<Transaction> transactionList = new ArrayList<>();
    private TransactionAdapter adapter;
    private RecyclerView rvDeal;
    String name_wl;
    int id_wl;

    FloatingActionButton fabAddTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tb8 = findViewById(R.id.tb8);
        setSupportActionBar(tb8);
        setTitle("Giao dịch Demo");

        bindView();

        addControls();

        addEvents();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }

    private void bindView() {

        ViewPager pager = findViewById(R.id.vpTransaction);
        PageTransactionAdapter pageTransactionAdapter = new PageTransactionAdapter(getSupportFragmentManager());

        for (int i = NUM_TAB; i > 0; i--) {
            Calendar calendar = getCalendarAt(i);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            pageTransactionAdapter.add(TransactionFragment.newInstance(month, year), "Tháng " + month + " / " + year);
        }

//        pageTransactionAdapter.add(TransactionFragment.newInstance(10, 2019), "Thang 10");
//        pageTransactionAdapter.add(TransactionFragment.newInstance(11, 2019), "Thang 11");
        pager.setAdapter(pageTransactionAdapter);

        TabLayout tabMonTransaction = findViewById(R.id.tabMonthTransaction);
        tabMonTransaction.setupWithViewPager(pager);

        pager.setCurrentItem(NUM_TAB - 1);
    }
    private Calendar getCalendarAt(int i) {
        Calendar calendar = Calendar.getInstance();
        Log.e("calendar: ", calendar.get(Calendar.MONTH)+"");
        Log.e("calendar: ", calendar.get(Calendar.YEAR)+"");
        calendar.add(Calendar.MONTH, 1 - i);

        return calendar;
    }


    private void addEvents() {
        Intent intent=getIntent();
        name_wl=intent.getStringExtra("nameWL");
        id_wl=intent.getIntExtra("idWL",-1);
        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ManHinhGiaoDich.class);
                intent.putExtra("nameWL",name_wl);
                intent.putExtra("idWL",id_wl);
                startActivityForResult(intent,1);
                Toast.makeText(HomeActivity.this, "Open Add Transaction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        fabAddTransaction = findViewById(R.id.fabAddTransaction);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuhd:
                //code xử lý khi bấm menu2
                break;
            case R.id.menuvi:
                Intent intent= new Intent(HomeActivity.this,ManHinhVi.class);
                startActivity(intent);
                break;
            case R.id.menunhom:
                Intent intent2= new Intent(HomeActivity.this,ManHinhNhom.class);
                intent2.putExtra("nameWL",name_wl);
                startActivityForResult(intent2,101);
                break;
            case R.id.menudangxuat:
                LoginManager.getInstance().logOut();
                Intent intent1= new Intent(HomeActivity.this,ManHinhDangNhap.class);
                startActivity(intent1);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
