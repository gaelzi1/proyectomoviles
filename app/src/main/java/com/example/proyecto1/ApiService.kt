package com.example.proyecto1
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("buscar")
    fun buscarRespuesta(@Body pregunta: Pregunta): Call<Respuesta>
}
