package id.co.gitsolution.kamus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.co.gitsolution.kamus.R;
import id.co.gitsolution.kamus.model.ModelKamus;
import id.co.gitsolution.kamus.view.DetailActivity;

public class AKamus extends RecyclerView.Adapter<AKamus.viewHolderMenu> {

    private Context context;
    private List<ModelKamus> kamusList;
    private List<ModelKamus> kamusListSearch = new ArrayList<>();

    public AKamus(Context context, List<ModelKamus> kamusList) {
        this.context = context;
        this.kamusList = kamusList;
        this.kamusListSearch.addAll(kamusList);
    }

    @Override
    public AKamus.viewHolderMenu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new viewHolderMenu(view);
    }

    @Override
    public void onBindViewHolder(final AKamus.viewHolderMenu holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvKamus.setText(kamusListSearch.get(position).getKamus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailActivity.class)
                        .putExtra("data", kamusListSearch.get(position)));
            }
        });
    }


    @Override
    public int getItemCount() {
        return kamusListSearch.size();
    }

    class viewHolderMenu extends RecyclerView.ViewHolder {

        private TextView tvKamus;

        viewHolderMenu(View itemView) {
            super(itemView);
            tvKamus = itemView.findViewById(R.id.tvKamus);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        kamusListSearch.clear();
        if (charText.length() == 0) {
            kamusListSearch.addAll(kamusList);
        } else {
            for (ModelKamus wp : kamusList) {
                if (wp.getKamus().toLowerCase(Locale.getDefault()).contains(charText)) {
                    kamusListSearch.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}