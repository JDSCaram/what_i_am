package br.com.jdscaram.whatiam.presentation.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import br.com.jdscaram.whatiam.R
import br.com.jdscaram.whatiam.presentation.main.model.GenderUiModel
import cn.iwgang.countdownview.CountdownView
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var countDownView: CountdownView
    private lateinit var genderAnimation: LottieAnimationView
    private lateinit var description: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        countDownView = view.findViewById(R.id.countdownView)
        genderAnimation = view.findViewById(R.id.gender_animation)
        description = view.findViewById(R.id.description)
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
        countDownView.setOnCountdownEndListener {
            viewModel.onCountdownIsOver()
        }
    }

    private fun onGenderRevelation(gender: GenderUiModel?) {
        gender?.let { uiModel ->
            countDownView.visibility = View.GONE
            genderAnimation.visibility = View.VISIBLE
            description.text = getString(uiModel.descriptionRes)
            description.setTextColor(ContextCompat.getColor(requireContext(), uiModel.color))
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}