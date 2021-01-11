package com.example.traffic

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.traffic.databinding.FragmentTrafficBinding

class TrafficFragment : Fragment() {
    private lateinit var binding: FragmentTrafficBinding
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var finishTimer: Runnable
    private lateinit var activity: MainActivityCalls

    private @Volatile
    var state: ColorTypes? = null

    companion object {
        fun newInstance(): TrafficFragment {
            return TrafficFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrafficBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun startTimer(colorTypes: ColorTypes, time: Int) {
        finishTimer = object : Runnable {
            override fun run() {
                state = colorTypes
                when (colorTypes) {
                    ColorTypes.RED -> {
                        binding.trafficColorYellow.visibility = INVISIBLE
                        binding.trafficColorGreen.visibility = INVISIBLE
                        binding.trafficColorRed.visibility = VISIBLE
                        startTimer(ColorTypes.YELLOW, 3)
                    }
                    ColorTypes.YELLOW -> {
                        binding.trafficColorRed.visibility = INVISIBLE
                        binding.trafficColorGreen.visibility = INVISIBLE
                        binding.trafficColorYellow.visibility = VISIBLE
                        startTimer(ColorTypes.GREEN, 3)
                    }
                    ColorTypes.GREEN -> {
                        binding.trafficColorRed.visibility = INVISIBLE
                        binding.trafficColorYellow.visibility = INVISIBLE
                        binding.trafficColorGreen.visibility = VISIBLE
                        startTimer(ColorTypes.YELLOW_GREEN, 3)
                    }
                    ColorTypes.YELLOW_GREEN -> {
                        binding.trafficColorRed.visibility = INVISIBLE
                        binding.trafficColorGreen.visibility = INVISIBLE
                        binding.trafficColorYellow.visibility = VISIBLE
                        startTimer(ColorTypes.RED, 3)
                    }
                }
            }
        }
        handler.postDelayed(finishTimer, time * 1000.toLong())
    }

    fun stopTimer() {
        handler.removeCallbacks(finishTimer)
        binding.trafficColorRed.visibility = VISIBLE
        binding.trafficColorGreen.visibility = VISIBLE
        binding.trafficColorYellow.visibility = VISIBLE
    }

    private fun initViews() {
        activity = getActivity() as MainActivityCalls
        binding.trafficColorRed.setOnClickListener {
            if (binding.trafficColorRed.isVisible && !binding.trafficColorYellow.isVisible && !binding.trafficColorGreen.isVisible) dialogShowingActions(resources?.getString(R.string.text_color_red))
        }
        binding.trafficColorYellow.setOnClickListener {
            if (binding.trafficColorYellow.isVisible && !binding.trafficColorRed.isVisible && !binding.trafficColorGreen.isVisible) dialogShowingActions(resources?.getString(R.string.text_color_yellow))
        }
        binding.trafficColorGreen.setOnClickListener {
            if (binding.trafficColorGreen.isVisible && !binding.trafficColorRed.isVisible && !binding.trafficColorYellow.isVisible) dialogShowingActions(resources?.getString(R.string.text_color_green))
        }
    }

    fun pauseTimer() {
        handler.removeCallbacks(finishTimer)
    }

    fun continueTimer() {
        handler.removeCallbacksAndMessages(null)
        startTimer(state!!, 0)
    }

    private fun dialogShowingActions(text:String) {
        handler.removeCallbacks(finishTimer)
        activity.showDialog(text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(finishTimer)
    }
}