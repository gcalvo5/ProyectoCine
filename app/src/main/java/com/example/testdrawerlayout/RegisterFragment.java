package com.example.testdrawerlayout;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.testdrawerlayout.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    Button botonRegistrar;
    private NavController navController;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        botonRegistrar = binding.authButtonRegistrar;
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // puede que genere problemas hacer el insert a firebase y a la base de datos a la vez
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.RegisterEmailEditText.getText().toString(),binding.RegisterContraseAEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "El usuario se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                            navController = Navigation.findNavController((Activity) view.getContext(),R.id.nav_host_fragment_content_main);
                            navController.navigate(R.id.nav_authFragment);
                            RetrofitMethods methods = RetrofitClient.getRetrofitInstance().create(RetrofitMethods.class);
                            Call<UsuariosDataModel> call = methods.insertarUsuario(binding.RegisterNombreDeUsuarioEditText.getText().toString(),binding.RegisterEmailEditText.getText().toString(),binding.RegisterContraseAEditText.getText().toString());
                            call.enqueue(new Callback<UsuariosDataModel>() {

                                @Override
                                public void onResponse
                                        (Call < UsuariosDataModel> call, Response< UsuariosDataModel> response)
                                {
                                    Log.e("on response code", "" + response.code());
                                }

                                @Override
                                public void onFailure (Call < UsuariosDataModel> call, Throwable t){
                                    Log.e("on failure", t.getMessage());
                                }

                            });
                        }
                        else {
                            Toast.makeText(getContext(), "No se ha podido registrar al usuario "+ task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}