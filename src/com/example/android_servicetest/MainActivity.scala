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
import android.widget.Toast
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
    var _service: IBinder = null

    var conn = new ServiceConnection() {
      override def onServiceDisconnected(name: ComponentName): Unit = {
        println("ServiceConnection is onServiceDisconnected invoke!")
      }
      override def onServiceConnected(name: ComponentName, service: IBinder): Unit = {
        _service = service
        println("ServiceConnection is onServiceConnected is invoke!")
      }
    }
    bindBtn.onClick({
      bindService(intent, conn, Context.BIND_AUTO_CREATE)
    })

    unbindBtn.onClick({
      unbindService(conn)
    })

    getdataBtn onClick {
      if (_service != null) {
        val myBinder = _service.asInstanceOf[MyBinder]
        Toast.makeText(this, "获取当前的count值为" + myBinder.count, Toast.LENGTH_SHORT).show
      } else {
        Toast.makeText(this, "还没有绑定", Toast.LENGTH_SHORT).show
      }
    }
  }
}