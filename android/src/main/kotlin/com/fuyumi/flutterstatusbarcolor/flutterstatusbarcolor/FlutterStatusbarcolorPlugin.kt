package com.fuyumi.flutterstatusbarcolor.flutterstatusbarcolor

import android.os.Build
import android.app.Activity
import android.view.View
import android.animation.ValueAnimator
import android.content.Context
import android.view.Window
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry


public class FlutterStatusbarcolorPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    private var activity: Activity? = null;

    companion object {

        @JvmStatic
        fun registerWith(registrar: PluginRegistry.Registrar) {
            val channel = MethodChannel(registrar.messenger(), "plugins.fuyumi.com/statusbar")
            channel.setMethodCallHandler(FlutterStatusbarcolorPlugin())
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result): Unit {
        val window: Window? = activity?.window;
        if (window == null) {
            return result.success(null)
        }
        when (call.method) {
            "getstatusbarcolor" -> {
                var statusBarColor: Int = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarColor = window.statusBarColor
                }
                result.success(statusBarColor)
            }
            "setstatusbarcolor" -> {
                val statusBarColor: Int = call.argument("color")!!
                val animate: Boolean = call.argument("animate")!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (animate) {
                        val colorAnim = ValueAnimator.ofArgb(window.statusBarColor, statusBarColor)
                        colorAnim.addUpdateListener { anim -> window.statusBarColor = anim.animatedValue as Int }
                        colorAnim.setDuration(300)
                        colorAnim.start()
                    } else {
                        window.statusBarColor = statusBarColor
                    }
                }
                result.success(null)
            }
            "setstatusbarwhiteforeground" -> {
                val usewhiteforeground: Boolean = call.argument("whiteForeground")!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (usewhiteforeground) {
                        window.decorView.systemUiVisibility =
                            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    } else {
                        window.decorView.systemUiVisibility =
                            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                }
                result.success(null)
            }
            "getnavigationbarcolor" -> {
                var navigationBarColor: Int = 0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    navigationBarColor = window.navigationBarColor
                }
                result.success(navigationBarColor)
            }
            "setnavigationbarcolor" -> {
                val navigationBarColor: Int = call.argument("color")!!
                val animate: Boolean = call.argument("animate")!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (animate) {
                        val colorAnim = ValueAnimator.ofArgb(window.navigationBarColor, navigationBarColor)
                        colorAnim.addUpdateListener { anim -> window.navigationBarColor = anim.animatedValue as Int }
                        colorAnim.setDuration(300)
                        colorAnim.start()
                    } else {
                        window.navigationBarColor = navigationBarColor
                    }
                }
                result.success(null)
            }
            "setnavigationbarwhiteforeground" -> {
                val usewhiteforeground: Boolean = call.argument("whiteForeground")!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (usewhiteforeground) {
                        window.decorView.systemUiVisibility =
                            window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
                    } else {
                        window.decorView.systemUiVisibility =
                            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    }
                }
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        val channel = MethodChannel(binding.binaryMessenger, "plugins.fuyumi.com/statusbar")
        channel.setMethodCallHandler(this)
    }


    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {

        val channel = MethodChannel(binding.binaryMessenger, "plugins.fuyumi.com/statusbar")
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivity() {
        activity = null;
    }
}
