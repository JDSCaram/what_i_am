package br.com.jdscaram.whatiam.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.presentation.main.model.GenderUiModel
import cn.iwgang.countdownview.CountdownView
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint

private const val LAST_15sec = 15

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var countDownView: CountdownView
    private lateinit var genderAnimation: LottieAnimationView
    private lateinit var description: TextView
    private lateinit var finalCountdown: TextView
    private lateinit var title: TextView
    private lateinit var motionLayout: MotionLayout
    private lateinit var lastCountdown: MotionLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        countDownView = view.findViewById(R.id.countdownView)
        genderAnimation = view.findViewById(R.id.gender_animation)
        description = view.findViewById(R.id.description)
        title = view.findViewById(R.id.title)
        lastCountdown = view.findViewById(R.id.final_countdown)
        finalCountdown = lastCountdown.findViewById(R.id.final_countdown_text)
        motionLayout = view.findViewById(R.id.scene_layout)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countDownTime.observe(viewLifecycleOwner) { time ->
            countDownView.visibility = View.VISIBLE
            if (time <= 0L) {
                viewModel.onCountdownIsOver()
            } else {
                countDownView.start(time)
            }
        }
        viewModel.gender.observe(viewLifecycleOwner) {
            onGenderRevelation(it)
        }
        countDownView.setOnCountdownIntervalListener(1000) { cv, time ->
            val remainTime = (time / 1000).toInt()
            if (remainTime <= 0) {
                viewModel.onCountdownIsOver()
            } else if (remainTime < LAST_15sec) {
                countDownView.visibility = View.INVISIBLE
                finalCountdown.visibility = View.VISIBLE
                lastCountdown.visibility = View.VISIBLE
                finalCountdown.text = "$remainTime"
            }
        }
    }

    private fun onGenderRevelation(gender: GenderUiModel?) {
        gender?.let { uiModel ->
            motionLayout.visibility = View.GONE
            finalCountdown.visibility = View.GONE
            lastCountdown.visibility = View.GONE
            countDownView.visibility = View.GONE
            title.visibility = View.VISIBLE
            genderAnimation.visibility = View.VISIBLE
            description.text = getString(uiModel.descriptionRes)
            description.setTextColor(ContextCompat.getColor(requireContext(), uiModel.color))
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}