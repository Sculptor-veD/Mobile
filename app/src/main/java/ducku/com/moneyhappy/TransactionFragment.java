package ducku.com.moneyhappy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ducku.com.moneyhappy.adapter.TransactionAdapter;
import ducku.com.moneyhappy.model.Preferences;
import ducku.com.moneyhappy.model.Transaction;

public class TransactionFragment extends Fragment {

    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";

    public static TransactionFragment newInstance(int month, int year) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle agrs = new Bundle();
        agrs.putInt(KEY_MONTH, month);
        agrs.putInt(KEY_YEAR, year);
        fragment.setArguments(agrs);
        return fragment;
    }

    private int month, year;

    public List<Transaction> transactionList =  new ArrayList<>();
    private TransactionAdapter adapter;
    private RecyclerView rvTransaction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        month = getArguments().getInt(KEY_MONTH);
        year = getArguments().getInt(KEY_YEAR);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.transaction_fragment, null);

        RecyclerView rvTransaction = view.findViewById(R.id.rvTransaction);
        CardView cardView = view.findViewById(R.id.clickViewReport);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startdate = year+"-"+month+"-01";
                String enddate = year+"-"+month+"-"+getNumDayOfMont(month,year);
                TextView textViewTong = view.findViewById(R.id.tongcong);
                String tongcong = textViewTong.getText().toString();
                if(!transactionList.isEmpty()) {
                    // Handler here
                    Toast.makeText(getContext(), month+"/"+year+" isDATA", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(), ViewReport.class);
                    intent.putExtra("startdate",startdate);
                    intent.putExtra("enddate",enddate);
                    intent.putExtra("thunhaprong",tongcong);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getContext(), month+"/"+year+" KHÔNG CÓ DỮ LIỆU", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new TransactionAdapter(getContext(), transactionList);
        rvTransaction.setLayoutManager(layoutManager);
        rvTransaction.setAdapter(adapter);

        loadData();
        return view;
    }

    private void loadData() {
        String userId = Preferences.getUser(getContext());
        String token = Preferences.getToken(getContext());
        String url= "act=gettransaction&account_id="+userId+"&month="+month+"&year="+year+"&token="+token;
        Log.e("URL", url);

        if(!transactionList.isEmpty()) {
            transactionList = new ArrayList<>();
        }

        new GetTransaction().execute(url);
        // change data to display on view
//        adapter.notifyDataSetChanged();

    }

    public int getNumDayOfMont(int _month, int _year) {

        switch (_month) {
            // các tháng 1, 3, 5, 7, 8, 10 và 12 có 31 ngày.
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            // các tháng 4, 6, 9 và 11 có 30 ngày
            case 4: case 6: case 9: case 11:
                return 30;
            // Riêng tháng 2 nếu là năm nhuận thì có 29 ngày, còn không thì có 28 ngày.
            case 2:
                if ((_year % 4 == 0 && _year % 100 != 0) || (_year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
        }
        return  0;
    }


    public class GetTransaction extends api {

        @Override
        public void onPostExecute(String s) {
            JSONArray array = null;
            try {
                array = new JSONArray(s);
                int thu = 0, chi = 0;
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object=array.getJSONObject(i);
                    int id=object.getInt("id");
                    int type_id=object.getInt("type_id");
                    int wallet_id=object.getInt("wallet_id");
                    int category_id=object.getInt("category_id");
                    String category_name=object.getString("category_name");
                    String descript=object.getString("descript");
                    int imgage_category=getResources().getIdentifier(object.getString("image_category"),"drawable", getContext().getPackageName());
                    int image_wallet=getResources().getIdentifier(object.getString("image_wallet"),"drawable", getContext().getPackageName());
                    int amount=object.getInt("amount");
                    String created=object.getString("created");
                    Transaction transaction = new Transaction(id, type_id, wallet_id, category_id, amount, imgage_category, image_wallet, category_name, created, descript);
                    transactionList.add(transaction);

                    if(type_id == 0) chi -= amount;
                    else              thu += amount;
                }

                adapter.notifyDataSetChanged();
                CardView cardView = getView().findViewById(R.id.clickViewReport);
                TextView textViewThu = getView().findViewById(R.id.value_tienvao);
                TextView textViewChi = getView().findViewById(R.id.value_tienra);
                TextView textViewTong = getView().findViewById(R.id.tongcong);
                if(thu != 0 || chi != 0) {
                    cardView.setVisibility(View.VISIBLE);
                    textViewThu.setText(thu + "đ");
                    textViewChi.setText(chi + "đ");
                    textViewTong.setText(thu + chi + "đ");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
