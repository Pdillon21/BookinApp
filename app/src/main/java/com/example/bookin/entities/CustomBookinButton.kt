package com.example.bookin.entities

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import com.example.bookin.R

class CustomBookinButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) {


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var animScaleUp : Animation = AnimationUtils.loadAnimation(this.context, R.anim.scale_up)
        var animScaleDown : Animation = AnimationUtils.loadAnimation(this.context,R.anim.scale_down)
        if (event != null) {
            if (event.action == MotionEvent.ACTION_DOWN){
                this.startAnimation(animScaleUp)
            } else if (event.action == MotionEvent.ACTION_UP){
                this.startAnimation(animScaleDown)
            }
        }
        return super.onTouchEvent(event)
        //ToDo hacer animacion sobre si mismo

    }
}