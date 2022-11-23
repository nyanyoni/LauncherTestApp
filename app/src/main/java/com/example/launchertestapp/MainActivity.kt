package com.example.launchertestapp

import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val webView = findViewById<View>(R.id.webView) as WebView
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("file:///android_asset/index2.html") // ローカルのhtmlファイルを指定

// WebView内のJavaScriptの実行を許可
        webView.getSettings().setJavaScriptEnabled(true)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                // 処理
                Toast.makeText(this@MainActivity, "!!!!!呼ばれた", Toast.LENGTH_LONG).show()
                Log.e("!!!!!", "呼ばれた！！！")
                result.cancel()
                return true
            }
        }

//        showSettingsLanguage()
    }

    private fun showSettingsLanguage() {
        val _Intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        _Intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(_Intent)
    }
}