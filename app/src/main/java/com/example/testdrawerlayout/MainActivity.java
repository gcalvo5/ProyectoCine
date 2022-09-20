package com.example.testdrawerlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.testdrawerlayout.databinding.ActivityMainBinding;
import com.example.testdrawerlayout.databinding.NavHeaderBinding;
import com.google.android.material.navigation.NavigationView;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    public NavController navController = null;
    private NavigationView navigationView;
    private ArrayList<String> localDataSet;
    private ArrayList<String> localDataSet2;
    private NavHeaderBinding navHeaderBinding;
    private TextView correoUsuarioTextView, nombreUsuarioTextView;
    private RecyclerView recycler;
    public static ArrayList<DataModel> data = new ArrayList<DataModel>();
    private boolean dataRecived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navHeaderBinding = NavHeaderBinding.inflate(getLayoutInflater());


        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;

        navController = Navigation.findNavController(this,R.id.nav_host_fragment_content_main);

        navigationView.setNavigationItemSelectedListener(new
        NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                drawerLayout.closeDrawers();

                switch (item.getItemId())
                {
                    case R.id.home_menu:{
                        navController.navigate(R.id.nav_home);
                        break;
                    }
                    case R.id.pelicula_menu:{
                        navController.navigate(R.id.nav_pelicula);
                        break;
                    }
                }
                return true;
            }
        });

        cargarPreferencias();
    }
    public void ConfigButton(View view){
        Toast.makeText(this,"configuracion", Toast.LENGTH_SHORT).show();
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
        popupMenu.getMenuInflater().inflate(R.menu.config_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){


                    default:
                        return false;
                }

            }
        });
        popupMenu.show();
    }
    public void boton(View view){
        drawerLayout.open();
    }


    private void cargarPreferencias(){
        // necesito documentar esto del navigation view para modificar el header y tambien sin duda alguna las shared preferences
        View headerView = navigationView.getHeaderView(0);
        correoUsuarioTextView = headerView.findViewById(R.id.correoUsuario);
        nombreUsuarioTextView = headerView.findViewById(R.id.nombreUsuario);
        SharedPreferences preferences = this.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        correoUsuarioTextView.setText(preferences.getString("correoUsuario","Email"));
        Toast.makeText(this,preferences.getString("correoUsuario","Email"), Toast.LENGTH_SHORT).show();
        nombreUsuarioTextView.setText(preferences.getString("nombreUsuario","Nombre de usuario"));
        Toast.makeText(this,"configuracion", Toast.LENGTH_SHORT).show();

    }
}