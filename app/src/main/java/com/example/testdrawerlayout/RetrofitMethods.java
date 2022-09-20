package com.example.testdrawerlayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitMethods {
    @GET("registro.php")
    Call<ArrayList<DataModel>> getAllData();
    @GET("registroComentarios.php")
    Call<ArrayList<ComentariosDataModel>> getAllDataComentarios(@Query("pelicula_id") String pelicula_id);
    @POST("insertarComentarios.php")
    @FormUrlEncoded
    Call<ComentariosDataModel> insertarComentario(@Field("pelicula_id") String pelicula_id,
                        @Field("comentario") String comentario);

    @GET("registroUsuario.php")
    Call<UsuariosDataModel> getUser(@Query("correo") String correo);

    @POST("insertarUsuario.php")
    @FormUrlEncoded
    Call<UsuariosDataModel> insertarUsuario(@Field("nombre") String nombre,
                                                  @Field("correo") String correo,
                                               @Field("contrasena") String contrasena);
}
