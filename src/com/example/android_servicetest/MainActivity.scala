package com.example.android_servicetest

import com.example.android_servicetest.CommonUtil._
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.content.Context

class MainActivity extends Activity with RichActivity {
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.service_main_layout)

    val bindBtn = get[Button](R.id.bind)
    val unbindBtn = get[Button](R.id.unbind)
    val startBtn = get[Button](R.id.start)
    val stopBtn = get[Button](R.id.stop)
    val getdataBtn = get[Button](R.id.getdata)

    val intent = new Intent
    intent.setAction("com.example.android_servicetest.MyService")
    startBtn.onClick({
      startService(intent)
    })
    stopBtn.onClick({
      stopService(intent)
    })

    val conn = new ServiceConnection() {
      override def onServiceDisconnected(name: ComponentName): Unit = {
        println("ServiceConnection is onServiceDisconnected invoke!")
      }
      override def onServiceConnected(name: ComponentName, service: IBinder): Unit = {
        println("ServiceConnection is onServiceConnected is invoke!")
      }
    }
    bindBtn.onClick({
      bindService(intent, conn, Context.BIND_AUTO_CREATE)
    })

    unbindBtn.onClick({
      unbindService(conn)
    })
  }
}