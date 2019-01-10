package davidhwang.raiwayvillage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.Toast







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
        mSpn.adapter = arrAdapSpn //將宣告好的 Adapter 設定給 Spinner

        mSpn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position==0){
                    access_0()
                }else if(position==1){
                    access_1()
                }else if(position==2){
                    access_2()
                }else if(position==3){
                    access_3()
                }else if(position==4){
                    access_4()
                }
            }

        }

    }


    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        startActivity(intent)
    }

    private fun access_0(){
        val intent = Intent()
        intent.setClass(this,
                env0::class.java)
        startActivity(intent)
    }

    private fun access_1(){
        val intent = Intent()
        intent.setClass(this,
                env1::class.java)
        startActivity(intent)
    }

    private fun access_2(){
        val intent = Intent()
        intent.setClass(this,
                env2::class.java)
        startActivity(intent)
    }

    private fun access_3(){
        val intent = Intent()
        intent.setClass(this,
                env3::class.java)
        startActivity(intent)
    }

    private fun access_4(){
        val intent = Intent()
        intent.setClass(this,
                env4::class.java)
        startActivity(intent)
    }
}
