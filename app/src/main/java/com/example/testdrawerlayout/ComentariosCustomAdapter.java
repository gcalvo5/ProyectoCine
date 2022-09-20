package com.example.testdrawerlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComentariosCustomAdapter  extends RecyclerView.Adapter<ComentariosCustomAdapter.ComentariosViewHolder> {
    private ArrayList<ComentariosDataModel> localDataSet;



    public static class ComentariosViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        public ComentariosViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.comentarios_textView);
        }
        public TextView getTextView() {
            return textView;
        }
    }

    public ComentariosCustomAdapter(ArrayList<ComentariosDataModel> dataset){
        localDataSet = dataset;
    }

    @NonNull
    @Override
    public ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comentarios_item_list, viewGroup, false);
        return new ComentariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentariosViewHolder holder, int position) {
        holder.getTextView().setText(localDataSet.get(position).getComentario());
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    public void clear(){
        localDataSet.clear();
    }
    public void add(ComentariosDataModel dataModel){
        localDataSet.add(dataModel);
    }
}
