package br.com.jdscaram.whatiam.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.presentation.main.MainFragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var container: FragmentContainerView
    private lateinit var animation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            container = findViewById(R.id.container)
            animation = findViewById(R.id.animation)
            viewModel.isSuccessRemoteConfig().observe(this) { isSuccessful ->
                animation.visibility = View.VISIBLE
                if (isSuccessful) {
                    initMainFragment()
                } else {
                    animation.repeatCount = LottieDrawable.INFINITE
                    animation.setAnimation(R.raw.error)
                    animation.playAnimation()
                }
            }
        }
    }

    private fun initMainFragment() {
        Handler().postDelayed({
            animation.visibility = View.GONE
            container.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.enter_right_to_left,
                    R.anim.exit_right_to_left,
                    R.anim.enter_left_to_right,
                    R.anim.enter_right_to_left
                )
                .replace(container.id, MainFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitNow()
        }, 2500)
    }
}