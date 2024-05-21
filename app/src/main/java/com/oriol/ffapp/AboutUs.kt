package com.oriol.ffapp


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class AboutUs : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)


        // Obtén las vistas
        val tvTitle = findViewById<View>(R.id.tvTitle)
        val tvDescription1 = findViewById<View>(R.id.tvDescription1)
        val tvDescription2 = findViewById<View>(R.id.tvDescription2)
        val tvDescription3 = findViewById<View>(R.id.tvDescription3)
        val logoYso = findViewById<View>(R.id.logoYso)


        // Llama a la función para animar las vistas
        animateView(tvTitle, 0)
        animateView(tvDescription1, 500)
        animateView(tvDescription2, 1000)
        animateView(tvDescription3, 1500)
        animateView(logoYso, 2000)
    }


    private fun animateView(view: View, delay: Long) {
        view.alpha = 0f
        view.translationY = 50f


        val animatorSet = AnimatorSet()
        val fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
        val translationAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 50f, 0f)


        animatorSet.playTogether(fadeAnimator, translationAnimator)
        animatorSet.duration = 1000
        animatorSet.startDelay = delay
        animatorSet.start()
    }
}
