package davidhwang.raiwayvillage

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text

class Info : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val home: ImageButton = findViewById(R.id.back_info)
        val btn_call: TextView = findViewById(R.id.btn_call)
        val btn_map: TextView = findViewById(R.id.btn_map)
        Linkify.addLinks(btn_call, Linkify.PHONE_NUMBERS)

        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener { accessHome()}

        btn_call.setOnClickListener {
            btn_call.setTextColor(Color.RED)
            openCallApp()}


        btn_map.setOnClickListener {
            btn_map.setTextColor(Color.RED)
            openMap()}

    }

    override fun onRestart() {
        super.onRestart()
        var btn: TextView = findViewById(R.id.btn_call)
        btn.setTextColor(Color.BLUE)

        btn=findViewById(R.id.btn_map)
        btn.setTextColor(Color.BLUE)
    }

    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        startActivity(intent)
    }

    private fun openCallApp() {
        val uri: Uri = Uri.parse("tel:03-5628933")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private fun openMap() {
        val uri: Uri = Uri.parse("geo:24.8041358,120.9769182")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

}
