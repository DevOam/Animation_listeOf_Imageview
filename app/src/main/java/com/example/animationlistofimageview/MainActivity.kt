package com.example.animationlistofimageview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.animationlistofimageview.R

class MainActivity : AppCompatActivity() {

    private lateinit var imageViews: MutableList<ImageView>
    private val imageResources = listOf(
        R.drawable.img_fitness5,
        R.drawable.img_fitness4,
        R.drawable.img_fitness3,
        R.drawable.img_fitness2,
        R.drawable.img_fitness1
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the list of ImageViews
        imageViews = mutableListOf(
            findViewById(R.id.imageView),
            findViewById(R.id.imageView),
            findViewById(R.id.imageView),
            findViewById(R.id.imageView),
            findViewById(R.id.imageView)

        )

        // Start the animation
        startAnimation()
    }

    private fun startAnimation() {
        // Get the current ImageView
        val currentImageView = imageViews[currentIndex]

        // Load the next drawable
        val nextIndex = (currentIndex + 1) % imageResources.size
        val nextDrawable = resources.getDrawable(imageResources[nextIndex], null)

        // Configure the animation
        val anim = ObjectAnimator.ofPropertyValuesHolder(
            currentImageView,
            PropertyValuesHolder.ofFloat(ImageView.ALPHA, 1.0f, 0.0f),
            PropertyValuesHolder.ofFloat(ImageView.SCALE_X, 1.0f, 0.0f),
            PropertyValuesHolder.ofFloat(ImageView.SCALE_Y, 1.0f, 0.0f)
        )
        anim.duration = 2500

        // Animation end listener
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Display the next drawable
                currentImageView.setImageDrawable(nextDrawable)

                // Configure the new animation
                val newAnim = ObjectAnimator.ofPropertyValuesHolder(
                    currentImageView,
                    PropertyValuesHolder.ofFloat(ImageView.ALPHA, 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat(ImageView.SCALE_X, 0.0f, 1.0f),
                    PropertyValuesHolder.ofFloat(ImageView.SCALE_Y, 0.0f, 1.0f)
                )
                newAnim.duration = 1000

                // Start the new animation
                newAnim.start()

                // Update the current image index
                currentIndex = nextIndex

                // Start the next animation
                startAnimation()
            }
        })

        // Start the animation
        anim.start()
    }
}
