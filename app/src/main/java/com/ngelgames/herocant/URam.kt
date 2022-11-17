package com.ngelgames.herocant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ngelgames.herocant.databinding.ActivityUramBinding

class URam : AppCompatActivity() {
    lateinit var iGamerBind: ActivityUramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iGamerBind = ActivityUramBinding.inflate(layoutInflater)
        setContentView(iGamerBind.root)

        val nameTxt = iGamerBind.nameET

        iGamerBind.strtGm.setOnClickListener{
            if (nameTxt.equals("")) {
                Toast.makeText(this, "Name field is empty", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.putExtra("name", nameTxt.toString())
                startActivity(Intent(this, MachActivity::class.java))
                finish()

            }
        }
        iGamerBind.nameET.text

    }
}