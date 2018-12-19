package davidhwang.raiwayvillage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
//ActionBar actionBar = getActionBar();
        actionBar!!.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}
