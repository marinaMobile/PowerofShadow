package com.ngelgames.herocant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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

            if (TextUtils.isEmpty(nameTxt.text.toString())) {
                nameTxt.error = "Field is empty"
            } else {
                val intent = Intent(this, MachActivity::class.java)
                intent.putExtra("name", nameTxt.text.toString())
                startActivity(intent)
                finish()
            }
            }
        }
    }