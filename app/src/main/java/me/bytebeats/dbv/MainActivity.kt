package me.bytebeats.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import me.bytebeats.dbv.DashBoardView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val dbv by lazy { findViewById<DashBoardView>(R.id.dbv) }
    private val progressBtn by lazy { findViewById<TextView>(R.id.btn_progress) }
    private val rimBtn by lazy { findViewById<TextView>(R.id.btn_rim_anim) }
    private val cursorBtn by lazy { findViewById<TextView>(R.id.btn_cursor_anim) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbv.animDuration = 1500
        progressBtn.setOnClickListener(this)
        rimBtn.setOnClickListener(this)
        cursorBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_progress -> dbv.progress += 5
            R.id.btn_rim_anim -> dbv.startRimAnim()
            R.id.btn_cursor_anim -> dbv.startCursorAnim()
        }
    }
}
