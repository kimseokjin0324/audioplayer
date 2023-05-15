package fastcampus.aop.part4.audioplayer.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {

    @GET("/v3/42d10dec-3b12-4bbd-ac21-f82551196f8f")
    fun listMusics(): Call<MusicDto>
}