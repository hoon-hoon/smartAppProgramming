package kr.ac.kumoh.ce.s20171225.w07intentdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kumoh.ce.s20171225.w07intentdata.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_RESULT = "image result"
        const val LIKE = 100
        const val DISLIKE = 101
        const val NONE = 0
    }
    private lateinit var main: ActivityImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityImageBinding.inflate(layoutInflater)
        setContentView(main.root)

        val res = when(intent.getStringExtra(MainActivity.KEY_NAME)){
            "mountain" -> R.drawable.mountain
            "sea" -> R.drawable.sea
            else -> R.drawable.ic_launcher_foreground
        }

        main.image.setImageResource(res)
    }
}

