package com.example.testdrawerlayout;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private NavController navController;
    private ArrayList<DataModel> localDataSet;
    private ArrayList<String> localDataSet2;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textView2;
        private final ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.itemid);
            textView2 = (TextView) view.findViewById(R.id.item2id);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getTextView2() {
            return textView2;
        }
        public ImageView getImageView() {
            return imageView;
        }
    }

    public CustomAdapter(ArrayList<DataModel> dataset,ArrayList<String> dataset2){
        localDataSet = dataset;
        localDataSet2 = dataset2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.getTextView().setText(localDataSet.get(position).getNombre());
        holder.getTextView().setMovementMethod(new ScrollingMovementMethod());
        holder.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController((Activity) view.getContext(),R.id.nav_host_fragment_content_main);
                Bundle bundle = new Bundle();
                bundle.putString("url_imgage",localDataSet.get(holder.getAdapterPosition()).getUrl_imagen());
                bundle.putString("description",localDataSet.get(holder.getAdapterPosition()).getDescripcion());
                bundle.putString("pelicula_id",localDataSet.get(holder.getAdapterPosition()).getId());
                navController.navigate(R.id.nav_pelicula_individual,bundle);
            }
        });
        holder.getTextView2().setText(localDataSet.get(position).getDirector());
        holder.getTextView2().setMovementMethod(new ScrollingMovementMethod());
        Picasso.get().load(localDataSet.get(position).getUrl_imagen()).into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    public void clear(){
        localDataSet.clear();
    }
    public void add(DataModel dataModel){
        localDataSet.add(dataModel);
    }
}
