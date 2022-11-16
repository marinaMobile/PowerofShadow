package com.ngelgames.herocant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngelgames.herocant.databinding.ActivityUramBinding

class URam : AppCompatActivity() {
    lateinit var iGamerBind: ActivityUramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iGamerBind = ActivityUramBinding.inflate(layoutInflater)
        setContentView(iGamerBind.root)
    }
}