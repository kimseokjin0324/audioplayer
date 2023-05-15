package fastcampus.aop.part4.audioplayer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import fastcampus.aop.part4.audioplayer.databinding.FragmentPlayerBinding
import fastcampus.aop.part4.audioplayer.service.MusicDto
import fastcampus.aop.part4.audioplayer.service.MusicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null
    private var isWatchingPlayListView = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding
        initPlayListButton(fragmentPlayerBinding)

        getVideoListFromServer()
    }

    private fun initPlayListButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playListImageView.setOnClickListener {
            //todo 만약에 서버에서 데이터가 다 불려오지 않는 형태일 경우 (즉 표시할 데이터가 없을 경우) playList로 넘어가지 않게
            fragmentPlayerBinding.playerViewGroup.isVisible = isWatchingPlayListView
            fragmentPlayerBinding.playerListViewGroup.isVisible = isWatchingPlayListView.not()
            isWatchingPlayListView = !isWatchingPlayListView
        }
    }

    private fun getVideoListFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusicService::class.java)
            .also {
                it.listMusics()
                    .enqueue(object : Callback<MusicDto> {
                        override fun onResponse(
                            call: Call<MusicDto>,
                            response: Response<MusicDto>
                        ) {
                            Log.d("PlayerFragment", "${response.body()}")

                            response.body()?.let { musicDto ->
                               val modelList =  musicDto.musics.mapIndexed { index, musicEntity ->
                                    musicEntity.mapper(index.toLong())

                                }


                            }
                        }

                        override fun onFailure(call: Call<MusicDto>, t: Throwable) {

                        }

                    })
            }
    }

    companion object {

        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }
}