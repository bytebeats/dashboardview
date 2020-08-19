package me.bytebeats.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import me.bytebeats.dbv.DashBoardView
import me.bytebeats.dbv.tplt.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val interpolators = arrayListOf(
        AccelerateDecelerateInterpolator(),
        AccelerateInterpolator(),
        BounceInterpolator(),
        CubicHermiteInterpolator(),
        CycleInterpolator(),
        DecelerateInterpolator(),
        LinearInterpolator(),
        OvershootInterpolator(),
        SmoothStepInterpolator(),
        SpringInterpolator()
    )

    private val dbv by lazy { findViewById<DashBoardView>(R.id.dbv) }
    private val progressBtn by lazy { findViewById<TextView>(R.id.btn_progress) }
    private val rimBtn by lazy { findViewById<TextView>(R.id.btn_rim_anim) }
    private val cursorBtn by lazy { findViewById<TextView>(R.id.btn_cursor_anim) }
    private val interpolatorBtn by lazy { findViewById<TextView>(R.id.btn_loop_interpolator) }
    private val spinner by lazy { findViewById<AppCompatSpinner>(R.id.spinner) }

    private var interpolatorIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbv.animDuration = 1500
        progressBtn.setOnClickListener(this)
        rimBtn.setOnClickListener(this)
        cursorBtn.setOnClickListener(this)
        interpolatorBtn.setOnClickListener(this)
        spinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            interpolators.map { it.javaClass.simpleName })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dbv.rimInterpolator = interpolators[position]
                rimBtn.performClick()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_progress -> dbv.progress += 5
            R.id.btn_rim_anim -> dbv.startRimAnim()
            R.id.btn_cursor_anim -> dbv.startCursorAnim()
            R.id.btn_loop_interpolator -> {
                interpolatorIdx += 1
                interpolatorIdx %= interpolators.size
                dbv.rimInterpolator = interpolators[interpolatorIdx]
            }
        }
    }


}
