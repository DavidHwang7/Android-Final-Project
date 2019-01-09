package davidhwang.raiwayvillage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import davidhwang.raiwayvillage.R.styleable.View

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.hide() //#303F9FDark  //#3F51B5Primary
        super.onCreate(savedInstanceState)
        val mWidth = resources.displayMetrics.widthPixels //手機的寬度(像素)
        val mHeight = resources.displayMetrics.heightPixels //手機的高度(像素)
        if(mWidth<=320&&mHeight<=480)
            setContentView(R.layout.activity_home_qvga)
        else
            setContentView(R.layout.activity_home_fhd)

        val intro_button: ImageButton = findViewById(R.id.intro_button)
        val camera_button: ImageButton = findViewById(R.id.camera_button)
        val info_button: ImageButton = findViewById(R.id.info_button)
        val history_button: ImageButton = findViewById(R.id.history_button)

        camera_button.setOnTouchListener(ButtonTouchLight())
        intro_button.setOnTouchListener(ButtonTouchDark())
        info_button.setOnTouchListener(ButtonTouchDark())
        history_button.setOnTouchListener(ButtonTouchLight())

        info_button.setOnClickListener { accessInfo() }
        history_button.setOnClickListener { accessHistory() }
        intro_button.setOnClickListener { accessEnv() }

    }

    private fun accessHistory(){
        val intent = Intent()
        intent.setClass(this,
                History::class.java)
        startActivity(intent)
    }

    private fun accessInfo(){
        val intent = Intent()
        intent.setClass(this,
                Info::class.java)
        startActivity(intent)
    }

    private fun accessEnv(){
        val intent = Intent()
        intent.setClass(this,
                Environment::class.java)
        startActivity(intent)
    }
}
