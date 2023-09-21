package kr.ac.kumoh.ce.w03lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.ac.kumoh.ce.w03lotto.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("LifeCycle!", "onCreate()")

//        setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        main.btnGenerate.setOnClickListener {
            main.num1.text = Random.nextInt(1, 46).toString()
            main.num2.text = Random.nextInt(1, 46).toString()
            main.num3.text = Random.nextInt(1, 46).toString()
            main.num4.text = Random.nextInt(1, 46).toString()
            main.num5.text = Random.nextInt(1, 46).toString()
            main.num6.text = Random.nextInt(1, 46).toString()

        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("LifeCycle!", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("LifeCycle!", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("LifeCycle!", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("LifeCycle!", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LifeCycle!", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("LifeCycle!", "onDestroy()")
    }
}