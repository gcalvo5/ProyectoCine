package com.example.testdrawerlayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testdrawerlayout.databinding.ActivityMainBinding;
import com.example.testdrawerlayout.databinding.FragmentPeliculaIndividualBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PeliculaIndividualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeliculaIndividualFragment extends Fragment {
    FragmentPeliculaIndividualBinding binding;
    ImageView imageView;
    TextView textView;
    RecyclerView recycler;
    ComentariosCustomAdapter adapter;
    ArrayList<ComentariosDataModel> localDataSet = new ArrayList<>();
    Button boton_enviar_comentario;
    EditText comentario_editText;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PeliculaIndividualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PeliculaIndividual.
     */
    // TODO: Rename and change types and number of parameters
    public static PeliculaIndividualFragment newInstance(String param1, String param2) {
        PeliculaIndividualFragment fragment = new PeliculaIndividualFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPeliculaIndividualBinding.inflate(getLayoutInflater());
        recycler = binding.RecyclerViewComentarios;
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        RetrofitMethods methods = RetrofitClient.getRetrofitInstance().create(RetrofitMethods.class);
        Call<ArrayList<ComentariosDataModel>> call = methods.getAllDataComentarios(getArguments().getString("pelicula_id"));
        call.enqueue(new Callback<ArrayList<ComentariosDataModel>>() {

            @Override
            public void onResponse
                    (Call < ArrayList < ComentariosDataModel >> call, Response< ArrayList < ComentariosDataModel >> response)
            {
                Log.e("on response code", "" + response.code());
                localDataSet = response.body();
                new AdapterHelper().update(adapter,localDataSet);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure (Call < ArrayList < ComentariosDataModel >> call, Throwable t){
                Log.e("on failure", t.getMessage());
            }

        });

        adapter = new ComentariosCustomAdapter(localDataSet);
        recycler.setAdapter(adapter);

        comentario_editText = binding.PeliculaIndividualEditText;
        boton_enviar_comentario = binding.botonEnviarComentario;
        // Esto me suelta un error porque se supone que el json esta mal formado y he añadido leinent para eso en el gsonConverter y suelta otro error
        // pero funciona de cualquier manera asi que lo voy a ignorar demomento pero cuidado mas adelante.
        boton_enviar_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ComentariosDataModel> callInsertar = methods.insertarComentario(getArguments().getString("pelicula_id"),comentario_editText.getText().toString());
                callInsertar.enqueue(new Callback<ComentariosDataModel>() {
                    @Override
                    public void onResponse(Call<ComentariosDataModel> call, Response<ComentariosDataModel> response) {
                        Log.e("on Success", "Comentario añadido correctamente");
                        Toast.makeText(getContext(),"Comentario añadido correctamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ComentariosDataModel> call, Throwable t) {
                        Log.e("on Failure", t.getMessage());
                        Toast.makeText(getContext(),"Fallo al añadir el comentario", Toast.LENGTH_SHORT).show();
                    }

                });

                comentario_editText.setText("");

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = binding.PeliculaIndividualImageView;
        textView = binding.PeliculaIndividualTextView;
        Picasso.get().load(getArguments().getString("url_imgage")).into(imageView);
        textView.setText(getArguments().getString("description"));
    }
}