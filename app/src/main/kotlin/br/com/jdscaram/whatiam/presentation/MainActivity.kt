package br.com.jdscaram.whatiam.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentContainerView
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.presentation.main.MainFragment
import com.airbnb.lottie.LottieAnimationView
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
                if (isSuccessful) {
                    initMainFragment()
                } else {
                    animation.setAnimation(R.raw.error)
                }
            }
        }
    }

    private fun initMainFragment() {
        Handler().postDelayed({
            animation.visibility = View.GONE
            container.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(container.id, MainFragment.newInstance())
                .commitNow()
        }, 3000)
    }
}