package com.giang.imagefirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    Context context;
    List<Database> databaseList;
    List<Database> databaseListOld;

    public DataAdapter(Context context, List<Database> databaseList) {
        this.context = context;
        this.databaseList = databaseList;
        this.databaseListOld = databaseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desgin_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Database database = databaseList.get(position);
        holder.tvAddress.setText(" " + database.getAddress());
        holder.tvName.setText(" " + database.getName());
        holder.tvMSSV_Khoa.setText(" "+database.getMSSV_Khoa());
        holder.tvTruong.setText("Trường : " + database.getTruong());
        holder.tvVien.setText("Viện      : " + database.getVien());
        holder.tvCpa.setText("Cpa : " + database.getCpa());
        holder.tvHovsTen.setText(" "+database.getHovsTen());

        String imageUri = null;
        imageUri = database.getImage();
        Picasso.get().load(imageUri).into(holder.imageView);
        Picasso.get().load(imageUri).into(holder.imgData);

        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (databaseList != null) {
            return databaseList.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, imgData;
        TextView tvName, tvAddress;
        TextView tvHovsTen, tvMSSV_Khoa, tvTruong, tvVien, tvCpa;

        FoldingCell foldingCell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_user);
            imgData = itemView.findViewById(R.id.img_data);
            tvName = itemView.findViewById(R.id.tv_name);
            tvHovsTen =itemView.findViewById(R.id.tv_ten);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvMSSV_Khoa = itemView.findViewById(R.id.tv_MSSV_khoa);
            tvTruong = itemView.findViewById(R.id.tv_truong);
            tvVien = itemView.findViewById(R.id.tv_vien);
            tvCpa = itemView.findViewById(R.id.tv_cpa);

            foldingCell = itemView.findViewById(R.id.folding_cell);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    databaseList = databaseListOld;
                }else {
                    List<Database> list = new ArrayList<>();
                    for (Database database : databaseListOld){
                        if (database.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(database);
                        }
                    }
                    databaseList = (ArrayList<Database>) list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = databaseList;


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                databaseList = (ArrayList<Database>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
