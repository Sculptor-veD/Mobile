package ducku.com.moneyhappy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ducku.com.moneyhappy.R;

public class IconAdapter extends ArrayAdapter<Integer> {
    Activity context; int resource; @NonNull List<Integer> objects;
    public IconAdapter(@NonNull Activity context, int resource, @NonNull List<Integer> objects) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(resource,null);
        ImageView imgicon=row.findViewById(R.id.imgicon);

        imgicon.setImageResource(objects.get(position));
        return row;
    }
}
