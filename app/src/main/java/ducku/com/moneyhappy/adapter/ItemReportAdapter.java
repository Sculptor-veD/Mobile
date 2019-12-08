package ducku.com.moneyhappy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ducku.com.moneyhappy.R;
import ducku.com.moneyhappy.model.ItemReport;

public class ItemReportAdapter extends RecyclerView.Adapter<ItemReportAdapter.ViewHolder> {
    private Context context;
    private List<ItemReport> itemReportList;

    public ItemReportAdapter(Context context, List<ItemReport> itemReportList) {
        this.context = context;
        this.itemReportList = itemReportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_report_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // get deal item from list at position
        ItemReport itemReport = itemReportList.get(position);

        // display info to item view holder
        viewHolder.tvNameCategory.setText(itemReport.get_name());
        viewHolder.tvAmount.setText(String.valueOf(itemReport.get_amount()) + " đ");
        viewHolder.ivThumbnailCategory.setImageResource(itemReport.get_img());
    }

    @Override
    public int getItemCount() {
        return itemReportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameCategory;
        TextView tvAmount;
        ImageView ivThumbnailCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCategory = itemView.findViewById(R.id.tvNameCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            ivThumbnailCategory = itemView.findViewById(R.id.ivThumbnailCategory);
        }
    }
}
