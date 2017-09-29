package com.dvitsolutions.singleapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import java.util.Arrays
import android.view.MotionEvent
import android.view.ViewGroup
import android.graphics.PixelFormat
import android.view.WindowManager
import android.view.Gravity
import android.widget.Toast
import android.content.Intent



class MainActivity : AppCompatActivity() {

    lateinit var buttonClick: Button

    val blockedKeys: List<Int> = ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preventStatusBarExpansion()

        buttonClick = findViewById(R.id.buttonClique) as Button

        buttonClick.setOnClickListener {
            fun onClick() {
                Toast.makeText(this, "Nanana", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBackPressed() {

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(blockedKeys.contains(event?.getKeyCode())) {
            return true
        } else {
            return super.dispatchKeyEvent(event)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            val closeDialog = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            sendBroadcast(closeDialog)
        }
    }

    fun preventStatusBarExpansion() {
        val manager = getApplicationContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val localLayoutParams = WindowManager.LayoutParams()
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        localLayoutParams.gravity = Gravity.TOP
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT

        val resId = getResources().getIdentifier("status_bar_height", "dimen", "android")
        var result = 0
        if (resId > 0) {
            result = getResources().getDimensionPixelSize(resId)
        } else {
            // Use Fallback size:
            result = 60 // 60px Fallback
        }

        localLayoutParams.height = result
        localLayoutParams.format = PixelFormat.TRANSPARENT

        val view = CustomViewGroup(this)
        manager.addView(view, localLayoutParams)
    }

    class CustomViewGroup(context: Context) : ViewGroup(context) {

        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

        override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
            return true
        }
    }
}
