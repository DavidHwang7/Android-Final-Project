package davidhwang.raiwayvillage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class Info : AppCompatActivity() {

    lateinit var bmp: Bitmap
    lateinit var InfoImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        bmp= getResizedBitmapFromDrawable(R.drawable.info0,this)
        InfoImage=findViewById(R.id.info_image)
        InfoImage.setImageBitmap(bmp)

        val home: ImageButton = findViewById(R.id.back_info)
        val btn_call: TextView = findViewById(R.id.btn_call)
        val btn_map: TextView = findViewById(R.id.btn_map)
        val btn_web: TextView = findViewById(R.id.btn_web)

        Linkify.addLinks(btn_call, Linkify.PHONE_NUMBERS)
        Linkify.addLinks(btn_map, Linkify.MAP_ADDRESSES)
        Linkify.addLinks(btn_web, Linkify.WEB_URLS)

        home.setOnTouchListener(ButtonTouchDark())
        home.setOnClickListener { accessHome()}

        btn_call.setOnClickListener {
            btn_call.setTextColor(Color.RED)
            openCallApp()}


        btn_map.setOnClickListener {
            btn_map.setTextColor(Color.RED)
            openMap()}

        btn_web.setOnClickListener {
            btn_web.setTextColor(Color.RED)
            openWeb()}
    }

    override fun onStop(){
        super.onStop()
        bmp.recycle()
        InfoImage.setImageDrawable(null)
        InfoImage.setImageBitmap(null)
    }

    override fun onRestart() {
        super.onRestart()
        var btn: TextView = findViewById(R.id.btn_call)
        btn.setTextColor(Color.BLUE)

        btn=findViewById(R.id.btn_map)
        btn.setTextColor(Color.BLUE)

        btn=findViewById(R.id.btn_web)
        btn.setTextColor(Color.BLUE)

        bmp= getResizedBitmapFromDrawable(R.drawable.info0,this)
        InfoImage.setImageBitmap(bmp)
    }

    private fun accessHome(){
        val intent = Intent()
        intent.setClass(this,
                Home::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }

    private fun openCallApp() {
        val uri: Uri = Uri.parse("tel:03-5628933")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }

    private fun openMap() {
        val uri: Uri = Uri.parse("geo:24.8041358,120.9769182?q=新竹鐵道藝術村")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }

    private fun openWeb() {
        val uri: Uri = Uri.parse("https://culture.hccg.gov.tw/ch/home.jsp?id=153&parentpath=0,145")
        val intent: Intent = Intent()
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setData(uri);
        startActivity(intent);
    }

}
