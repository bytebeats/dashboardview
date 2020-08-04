package me.bytebeats.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import me.bytebeats.dbv.DashBoardView

class MainActivity : AppCompatActivity() {

    private val dbv by lazy { findViewById<DashBoardView>(R.id.dbv) }
    private val btn by lazy { findViewById<Button>(R.id.btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            dbv.progress += 5
        }
    }
}
