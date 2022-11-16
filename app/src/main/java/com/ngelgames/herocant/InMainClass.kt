package com.ngelgames.herocant

import android.app.Application
import com.my.tracker.MyTracker
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import java.util.*


class InMainClass : Application() {
    companion object {
        const val dfwthyhyj = "81486002471202890054"
        const val jglfkdkdkgjd = "2bfc44b5-305b-49c1-92d9-7002a90fea3b"
        var appsKey = "appsKey"
        var appsCheck = "appsChecker"
        var C1: String? = "c11"

        var myID: String? = "myID"
        var instId: String? = "instID"
        var link = "link"
        var MAIN_ID: String? = ""
    }

    override fun onCreate() {
        super.onCreate()

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(jglfkdkdkgjd)

        Hawk.init(this).build()


        val settings = getSharedPreferences("PREFS_NAME", 0)

        val trackerParams = MyTracker.getTrackerParams()
        val trackerConfig = MyTracker.getTrackerConfig()
        val instID = MyTracker.getInstanceId(this)
        trackerConfig.isTrackingLaunchEnabled = true
        if (settings.getBoolean("my_first_time", true)) {
            val IDIN = UUID.randomUUID().toString()
            trackerParams.setCustomUserId(IDIN)
            Hawk.put(myID, IDIN)
            Hawk.put(instId, instID)
            settings.edit().putBoolean("my_first_time", false).apply()
        } else {
            val IDIN = Hawk.get(myID, "null")
            trackerParams.setCustomUserId(IDIN)
        }
        MyTracker.initTracker(dfwthyhyj, this)


    }
}
