package com.example.launchertestapp

import android.app.ActivityManagerNative
import android.app.IActivityManager
import android.app.backup.BackupManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val webView = findViewById<View>(R.id.webView) as WebView
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("file:///android_asset/index.html") // ローカルのhtmlファイルを指定
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

// WebView内のJavaScriptの実行を許可
        webView.getSettings().setJavaScriptEnabled(true)

        webView.addJavascriptInterface(WebAppInterface(this), "ffnTv")
//return
        showSettingsLanguage()
        return
       val a = createAppInfo(this)
        a.map {
            Log.e("!!!!!" , it.label)
        }


        val adapter = AppAdapter(layoutInflater, createAppInfo(this)) { view, info ->
            info.launch(this)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view) as RecyclerView
        recyclerView.adapter = adapter
        recyclerView.isGone = true
    }

    private fun showSettingsLanguage() {


       val am :IActivityManager  = ActivityManagerNative.getDefault();

       val config: Configuration  = am.configuration;
////      val loc:  Loc = mLocales[position];
////
        config.locale = Locale("en")
////
////        config.userSetLocale = true;
////
////
////
        am.updateConfiguration(config);
//
//        // Trigger the dirty bit for the Settings Provider.
//
        BackupManager.dataChanged("com.android.providers.settings");

//        val locale = Locale("zh-CH")
//        Locale.setDefault(locale)
//        val resources: Resources = getResources()
//        val config: Configuration = resources.getConfiguration()
//        config.setLocale(locale)
//        resources.updateConfiguration(config, resources.getDisplayMetrics())


//        val locale = Locale("en")
//        Locale.setDefault(locale)
//
//        val resources: Resources = getResources()
//
//        val configuration = resources.configuration
//        configuration.setLocale(locale)
//
////        resources.updateConfiguration(configuration, resources.displayMetrics)
//        createConfigurationContext(configuration)

        val locale = Locale("en")
        SwitchSystemLanguageUtils.changeLangue(locale)


//        val _Intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
//        _Intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(_Intent)
    }

    private fun createAppInfo(context: Context): List<AppInfo> {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
            .also { it.addCategory(Intent.CATEGORY_LAUNCHER) }
        return pm.queryIntentActivities(intent, PackageManager.MATCH_ALL)
//            .asSequence()
            .mapNotNull { it.activityInfo }
            .filter { it.packageName != context.packageName }
            .map {
                AppInfo(
                    it.loadIcon(pm) ?: getDrawable(R.drawable.ic_launcher_background)!!,
                    it.loadLabel(pm).toString(),
                    ComponentName(it.packageName, it.name)
                )
            }
            .sortedBy { it.label }
    }

    data class AppInfo(
        val icon: Drawable,
        val label: String,
        val componentName: ComponentName
    ){
        fun launch(context: Context) {
            try {
                val intent = Intent(Intent.ACTION_MAIN).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                    it.addCategory(Intent.CATEGORY_LAUNCHER)
                    it.component = componentName
                }
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }
}    /** Instantiate the interface and set the context  */
class WebAppInterface(private val mContext: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun startApp(packageName: String) {
        Log.e("呼ばれた!!!!!" , packageName)
        Toast.makeText(mContext, "startApp packageName = $packageName", Toast.LENGTH_SHORT).show()

        val pm: PackageManager = mContext.packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        mContext.startActivity(intent)
    }
    @JavascriptInterface
    fun startAppWithData(packageName: String, data: String) {
        Log.e("呼ばれた!!!!!" , packageName + data)
        Toast.makeText(mContext, "startAppWithData packageName = $packageName", Toast.LENGTH_SHORT).show()
    }
    @JavascriptInterface
    fun changeLocale(locale: String) {
        Log.e("changeLocale呼ばれた!!!!!" , locale)
        Toast.makeText(mContext, "changeLocale locale = $locale", Toast.LENGTH_SHORT).show()

        return
        val _Intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        _Intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(_Intent)

    }
    @JavascriptInterface
    fun changeLocale() {
        Log.e("changeLocale呼ばれた!!!!!", "!!!!")
        Toast.makeText(mContext, "changeLocale", Toast.LENGTH_SHORT).show()

        val _Intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        _Intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mContext.startActivity(_Intent)
    }
}
