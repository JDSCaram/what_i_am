package br.com.jdscaram.whatiam.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.presentation.main.model.GenderUiModel
import cn.iwgang.countdownview.CountdownView
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint

private const val LAST_11sec = 11

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
    private lateinit var sceneFinal: MotionLayout
    private lateinit var finalGroup: Group

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
        sceneFinal = view.findViewById(R.id.scene_final)
        finalGroup = view.findViewById(R.id.reveal_group)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countDownTime.observe(viewLifecycleOwner) { time ->
            countDownView.visibility = View.VISIBLE
            if (time <= 0L) {
                revealGender()
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
                revealGender()
            } else if (remainTime < LAST_11sec) {
                countDownView.visibility = View.GONE
                finalCountdown.visibility = View.VISIBLE
                lastCountdown.visibility = View.VISIBLE
                finalCountdown.text = "$remainTime"
            }
        }
    }

    private fun revealGender() {
        motionLayout.visibility = View.GONE
        finalCountdown.visibility = View.GONE
        lastCountdown.visibility = View.GONE
        countDownView.visibility = View.GONE

        sceneFinal.visibility = View.VISIBLE
        sceneFinal.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                // do nothing
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // do nothing
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.id.last_scene) {
                    viewModel.onCountdownIsOver()
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                // do nothing
            }

        })

    }

    private fun onGenderRevelation(gender: GenderUiModel?) {
        gender?.let { uiModel ->
            finalGroup.visibility = View.VISIBLE
            title.text = getString(uiModel.titleRes)
            description.text = getString(uiModel.descriptionRes)
            description.setTextColor(ContextCompat.getColor(requireContext(), uiModel.color))
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}