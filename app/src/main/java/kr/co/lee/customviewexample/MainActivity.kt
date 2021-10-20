package kr.co.lee.customviewexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var barView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val plusMinusView = findViewById<MyPlusMinusView>(R.id.customView)
        barView = findViewById(R.id.barView)

        // 인터페이스 구현 객체 View에 등록
        plusMinusView.setOnMyChangeListener(object : OnMyChangeListener {
            override fun onChange(value: Int) {
                when {
                    // 0 미만이면 빨간색
                    value < 0 -> barView.setBackgroundColor(Color.RED)
                    // 0 ~ 30
                    value in 0..30 -> barView.setBackgroundColor(Color.YELLOW)
                    // 31 ~ 60
                    value in 31..60 -> barView.setBackgroundColor(Color.BLUE)
                    // 60 초과
                    else -> barView.setBackgroundColor(Color.GREEN)
                }
            }
        })
    }
}