package com.example.testdrawerlayout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.testdrawerlayout.databinding.FragmentAuthBinding;
import com.example.testdrawerlayout.databinding.FragmentPeliculaIndividualBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {
    Button botonRegistrar;
    Button botonAutentificar;
    FragmentAuthBinding binding;
    UsuariosDataModel usuariosDataModel;
    private NavController navController;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthFragment newInstance(String param1, String param2) {
        AuthFragment fragment = new AuthFragment();
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
        binding = FragmentAuthBinding.inflate(getLayoutInflater());
        cargarPreferencias();
        botonRegistrar = binding.authButtonRegistrar;
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController((Activity) view.getContext(),R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_registerFragment);
            }
        });

        botonAutentificar = binding.authButtonAcceder;
        botonAutentificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController((Activity) view.getContext(),R.id.nav_host_fragment_content_main);

                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.AuthEmailEditText.getText().toString(),binding.authContrasenaEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            RetrofitMethods methods = RetrofitClient.getRetrofitInstance().create(RetrofitMethods.class);
                            Call<UsuariosDataModel> call = methods.getUser(binding.AuthEmailEditText.getText().toString());
                            call.enqueue(new Callback<UsuariosDataModel>() {

                                @Override
                                public void onResponse
                                        (Call < UsuariosDataModel> call, Response< UsuariosDataModel> response)
                                {
                                    // no me coge bien la respuesta del get miraaaar
                                    usuariosDataModel = response.body();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Usuario",usuariosDataModel);
                                    guardarPreferencias();
                                    navController.navigate(R.id.nav_home,bundle);
                                    Log.e("on response code auth", "" + response.code());
                                }

                                @Override
                                public void onFailure (Call < UsuariosDataModel> call, Throwable t){
                                    Log.e("on failure auth", t.getMessage());
                                }

                            });
                        }
                        else {
                            Toast.makeText(getContext(),"No se ha podido ingresar "+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        return binding.getRoot();
    }
    private void guardarPreferencias(){
        SharedPreferences preferences= getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombreUsuario",usuariosDataModel.getNombre());
        editor.putString("correoUsuario",usuariosDataModel.getCorreo());
        editor.putString("contrasenaUsuario",usuariosDataModel.getConstrasena());
        editor.putString("idUsuario",usuariosDataModel.getId());
        editor.commit();

    }
    private void cargarPreferencias(){
        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        binding.AuthEmailEditText.setText(preferences.getString("correoUsuario",""));
        binding.authContrasenaEditText.setText(preferences.getString("contrasenaUsuario",""));
    }
}