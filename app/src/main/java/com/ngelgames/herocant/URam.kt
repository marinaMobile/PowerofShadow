package com.ngelgames.herocant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngelgames.herocant.databinding.ActivityUramBinding

class URam : AppCompatActivity() {
    lateinit var iGamerBind: ActivityUramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iGamerBind = ActivityUramBinding.inflate(layoutInflater)
        setContentView(iGamerBind.root)

        iGamerBind.strtGm.setOnClickListener{
            startActivity(Intent(this, MachActivity::class.java))
            finish()
        }

    }
}