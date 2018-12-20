package davidhwang.raiwayvillage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
//ActionBar actionBar = getActionBar();
        actionBar!!.hide() //#303F9FDark  //#3F51B5Primary
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_qvga)

        val intro_button: ImageButton = findViewById(R.id.intro_button)
        val camera_button: ImageButton = findViewById(R.id.camera_button)
        val info_button: ImageButton = findViewById(R.id.info_button)
        val history_button: ImageButton = findViewById(R.id.history_button)

        camera_button.setOnTouchListener(ButtonTouchLight())
        intro_button.setOnTouchListener(ButtonTouchDark())
        info_button.setOnTouchListener(ButtonTouchDark())
        history_button.setOnTouchListener(ButtonTouchLight())

        history_button.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@Home, History::class.java)
            startActivity(intent) }



    }
}
