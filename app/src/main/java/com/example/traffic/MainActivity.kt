package com.example.traffic

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.traffic.databinding.ActivityMainBinding

class MainActivity : BaseActivity(), MainActivityCalls {
    private lateinit var binding: ActivityMainBinding
    private var textFlag = true
    private lateinit var fragment: TrafficFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        addFragment(R.id.traffic_container, TrafficFragment.newInstance(), null, false)
    }

    fun initViews() {
        binding.trafficBtn.setOnClickListener {
            fragment =
                supportFragmentManager.findFragmentById(R.id.traffic_container) as TrafficFragment
            if (textFlag) {
                fragment.startTimer(ColorTypes.RED, 0)
                binding.trafficBtn.setText(R.string.text_stop)
                textFlag = false
            } else {
                fragment.stopTimer()
                binding.trafficBtn.setText(R.string.text_start)
                textFlag = true
            }

        }
    }

    override fun showDialog(text: String) {
        DialogManager().showTrafficDialog(this) {
            when (it) {
                true -> {
                    replaceFragment(
                        R.id.traffic_container,
                        DescriptionFragment.newInstance(text),
                        null,
                        true
                    )
                    fragment.stopTimer()
                    textFlag = true
                    binding.trafficBtn.setText(R.string.text_start)
                    binding.trafficBtn.isEnabled = false
                }
                false -> {
                    fragment.continueTimer()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.trafficBtn.isEnabled = true
    }
}