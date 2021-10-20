package kr.co.lee.customviewexample

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

// 라이브러리에서 제공하는 뷰를 그대로 이용하며 변형시키기 위해 AppCompatTextView를 상속
class CustomTextView: AppCompatTextView {
    var value: Int = 0
        set(value) {
            field = value
            customTextColor()
        }

    // 생성자
    // 기본적으로 자바 코드로 생성해서 이용한다면 생성자는 하나만 정의해도 되지만
    // 레이아웃 XML 파일에 등록해 사용하려면 아래처럼 생성자 3개를 모두 정의해줘야 한다
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleArr: Int)
            : super(context, attrs, defStyleArr)

    // 프로퍼티 value의 값을 가지고 뷰의 TextColor 변경
    private fun customTextColor() {
        when {
            value < 0 -> this.setTextColor(Color.GREEN)
            value < 30 -> this.setTextColor(Color.BLUE)
            value < 60 -> this.setTextColor(Color.YELLOW)
            else -> this.setTextColor(Color.RED)
        }
    }
}