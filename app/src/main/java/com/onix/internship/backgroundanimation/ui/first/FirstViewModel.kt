package com.onix.internship.backgroundanimation.ui.first

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.onix.internship.backgroundanimation.events.SingleLiveEvent


class FirstViewModel() : ViewModel() {

    private val _navigationLiveEvent = SingleLiveEvent<NavDirections>()
    val navigationLiveEvent: LiveData<NavDirections> = _navigationLiveEvent

    fun animationOn(backgroundOne: ImageView, backgroundTwo: ImageView){
        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val width: Int = backgroundOne.width
            val translationX = width * progress
            backgroundOne.translationX = translationX
            backgroundTwo.translationX = translationX - width
        }
        animator.start()
    }
}