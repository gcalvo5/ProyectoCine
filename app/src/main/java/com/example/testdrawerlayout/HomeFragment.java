package com.example.testdrawerlayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testdrawerlayout.databinding.NavHeaderBinding;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ArrayList<DataModel> localDataSet = new ArrayList<DataModel>();
    private ArrayList<String> localDataSet2;
    private RecyclerView recycler;
    private View vista;
    private String a;
    private CustomAdapter adapter;
    private UsuariosDataModel usuariosDataModel;
    private TextView nombreUsuarioTextView;
    private TextView correoUsuarioTextView;
    private NavHeaderBinding navHeaderBinding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        vista = inflater.inflate(R.layout.fragment_home,container,false);
        recycler = (RecyclerView) vista.findViewById(R.id.recyclerid);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        final FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString("HomeFragment_Analytics","HomeFragment visitado");
        analytics.logEvent("HomeFragment_Analytics",bundle);




        RetrofitMethods methods = RetrofitClient.getRetrofitInstance().create(RetrofitMethods.class);
        Call<ArrayList<DataModel>> call = methods.getAllData();
        call.enqueue(new Callback<ArrayList<DataModel>>() {

            @Override
            public void onResponse
                    (Call < ArrayList < DataModel >> call, Response< ArrayList < DataModel >> response)
            {
                Log.e("on response code home", "" + response.code());
                localDataSet = response.body();
                new AdapterHelper().update(adapter,localDataSet);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure (Call < ArrayList < DataModel >> call, Throwable t){
                Log.e("on failure home", t.getMessage());
            }

        });

        adapter = new CustomAdapter(localDataSet,localDataSet2);
        recycler.setAdapter(adapter);
        // Inflate the layout for this fragment
        return vista;

    }


    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        Log.e("tag", "entro al onSaveInstanceState");
        outState.putSerializable("usuario",usuariosDataModel);
        outState.putString("a",a);
        super.onSaveInstanceState(outState);
    }
    private void cargarPreferencias(){
        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
    }
}