package davidhwang.raiwayvillage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView





class Environment : AppCompatActivity() {

    private val mSpn: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_environment)

        val home: ImageButton = findViewById(R.id.back_env)
        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener(android.view.View.OnClickListener() { accessHome()})

        val mSpn : Spinner = findViewById<View>(R.id.spn) as Spinner

        val arrAdapSpn = ArrayAdapter.createFromResource(this, //對應的Context
                R.array.env, //選項資料內容
                R.layout.spinner_item) //自訂getView()介面格式(Spinner介面未展開時的View)

        arrAdapSpn.setDropDownViewResource(R.layout.spinner_dropdown_item) //自訂getDropDownView()介面格式(Spinner介面展開時，View所使用的每個item格式)
        mSpn.setAdapter(arrAdapSpn) //將宣告好的 Adapter 設定給 Spinner
       // mSpn.setOnItemSelectedListener(spnOnItemSelected)

    }

    private val spnRegionOnItemSelected = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, v: View, position: Int, id: Long) {
            // TODO Auto-generated method stub
        }

        override fun onNothingSelected(arg0: AdapterView<*>) {
            // TODO Auto-generated method stub
        }
    }

    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        startActivity(intent)
    }
}
