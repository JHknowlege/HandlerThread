package com.jhzl.handlerthread

import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class HandlerThreadActivity : AppCompatActivity() {
    private val TAG = "HandlerThreadActivity"
    private var mCustomThreadHandler: Handler? = null
    private var mThreadHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_thread)
        findViewById<Button>(R.id.start_thread_btn).setOnClickListener {
            startHandlerWithThread()
        }

        findViewById<Button>(R.id.start_handler_thread_btn).setOnClickListener {
            startHandlerWithHandlerThread()
        }

        findViewById<Button>(R.id.quit_custom_handler_btn).setOnClickListener {
            mCustomThreadHandler?.looper?.quitSafely()
        }


        findViewById<Button>(R.id.send_message_custom_btn).setOnClickListener {
            mCustomThreadHandler?.sendEmptyMessage(12)
        }


        findViewById<Button>(R.id.send_message_standard_btn).setOnClickListener {
            mThreadHandler?.sendEmptyMessage(11)
        }


        findViewById<Button>(R.id.quit_standard_handler_btn).setOnClickListener {
            mThreadHandler?.looper?.quit()
        }

    }

    fun startHandlerWithThread() {
        Thread {
            Looper.prepare()
            mCustomThreadHandler = object : Handler(Looper.myLooper()!!) {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    Log.d(TAG,"mCustomThreadHandler handleMsg = >"+msg.what)
                }
            }
            Looper.loop()

        }.start()
    }

    fun startHandlerWithHandlerThread() {
        var handlerThread = HandlerThread("handlerThread")
        handlerThread.start()
        mThreadHandler =object :Handler(handlerThread.looper){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d(TAG,"mCustomThreadHandler handleMsg = >"+msg.what)

            }
        }
        Looper.loop()
    }


}