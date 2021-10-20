package kr.co.lee.customviewexample

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyPlusMinusView: View {
    // 증감 값
    var value: Int = 0
    // 화면 출력 이미지
    lateinit var plusBitmap: Bitmap
    lateinit var minusBitmap: Bitmap
    // 이미지가 화면에 출력되는 좌표 정보
    lateinit var plusRectDst: Rect
    lateinit var minusRectDst: Rect

    // value 출력 문자열 색상
    var textColor: Int? = null

    // Observer를 등록하기 위한 객체
    var listeners: ArrayList<OnMyChangeListener>? = null

    // 생성자 호출
    constructor(context: Context): super(context) {
        init(null)
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    // 생성자의 공통 코드
    private fun init(attrs: AttributeSet?) {
        // 이미지 획득
        plusBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.plus)
        minusBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.minus)
        
        // 이미지 출력 사각형 좌표 정보 설정
        // Rect의 생성자로 (left, top), (right, bottom) 정보 주기
        plusRectDst = Rect(10, 10, 210, 210)
        minusRectDst = Rect(400, 10, 600, 210)

        // custom 속성값 획득
        attrs?.let {
            // 속성 값 추출
            val a = context.obtainStyledAttributes(attrs, R.styleable.MyView)
            textColor = a.getColor(R.styleable.MyView_customTextColor, Color.RED)
        }
        // ArrayList 객체 생성
        listeners = ArrayList()
    }

    // Observer 등록을 위한 함수
    fun setOnMyChangeListener(listener: OnMyChangeListener) {
        listeners?.add(listener)
    }

    // 뷰 내부에서 크기 결정(가로 정보와 세로 정보가 매개변수로 들어온다)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 레이아웃에서 지정한 모드와 크기를 얻어온다
        // 모드는 다믕과 같다
        // MeasureSpec.At_MOST : 뷰 내부에서 지정하라는 의미(XML에서 wrap_content로 지정)
        // MeasureSpec.EXACTLY : 액티비티 쪽에서 크기를 결정한 경우, XML에서 fill_parent, match_parent등
        // MeasureSpec.UNSPECIFIED : 모드가 설정되지 않은 경우
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        
        // 뷰 크기를 지정하기 위한 변수
        var width = 0
        var height = 0
        
        // wrap_content인 경우
        if(widthMode == MeasureSpec.AT_MOST) {
            width = 700
        // fill_parent, match_parent인 경우
        } else if(widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        }

        if(heightMode == MeasureSpec.AT_MOST) {
            height = 250
        } else if(heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        }

        // 뷰의 크기를 정하는 메서드
        setMeasuredDimension(width, height)
    }

    // 뷰의 터치이벤트 정의
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 터치한 좌표의 x, y좌표
        val x = event?.x!!
        val y = event?.y!!

        // 플러스 아이콘이 터치된 거라면
        if(plusRectDst?.contains(x.toInt(), y.toInt())!! && event.action == MotionEvent.ACTION_DOWN) {
            // 데이터 변경
            value++
            // 화면 갱신(invalidate()를 호출하면 자동으로 onDraw() 함수가 호출된다)
            invalidate()
            listeners?.let {
                for(listener in it) {
                    // observer에 데이터 전달
                    listener.onChange(value)
                }
            }
            return true
        } else if(minusRectDst?.contains(x.toInt(), y.toInt())!! && event.action == MotionEvent.ACTION_DOWN) {
            value--
            invalidate()
            listeners?.let {
                for(listener in it) {
                    listener.onChange(value)
                }
            }
            return true
        }
        return false
    }

    // 뷰를 그리는 함수
    override fun onDraw(canvas: Canvas?) {
        // 화면 지우기
        canvas?.drawColor(Color.alpha(Color.CYAN))
        
        // 이미지의 사각형 정보
        val plusRectSource = Rect(0, 0, plusBitmap.width, plusBitmap.height)
        val minusRectSource = Rect(0, 0, minusBitmap.width, minusBitmap.height)

        val paint = Paint()

        // plus 이미지 그리기
        canvas?.drawBitmap(plusBitmap, plusRectSource, plusRectDst, null)

        // value 문자열 그리기
        paint.textSize = 80f
        if(textColor != null) {
            paint.color = textColor as Int
        }
        canvas?.drawText(value.toString(), 260f, 150f, paint)
        
        // minus 이미지 그리기
        canvas?.drawBitmap(minusBitmap, minusRectSource, minusRectDst, null)
    }
}