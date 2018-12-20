package davidhwang.raiwayvillage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Home : AppCompatActivity() {

	//Hello
//Hello from David

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
//ActionBar actionBar = getActionBar();
        actionBar!!.hide()//#303F9FDark  //#3F51B5Primary
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_fhd)
    }
}
