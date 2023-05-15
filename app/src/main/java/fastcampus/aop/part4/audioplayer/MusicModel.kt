package fastcampus.aop.part4.audioplayer

data class MusicModel(
    val id: Long,
    val track: String,
    val streamUrl: String,
    val artist: String,
    val coverUrl: String,
    val isPlaying: Boolean = false //-노래가 시작 되고 있는지 (default = False)
)