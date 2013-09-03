package com.example.android_servicetest

import android.os.IBinder
import android.app.Service
import android.content.Intent
import android.os.Binder

class MyService extends Service {
  val myBinder = new MyBinder
  var loop: Boolean = true

  class MyBinder extends Binder {
    var count = 0
    def MyBinder(): Unit = {
      println("MyBinder is created!")
    }

  }
  def onBind(intent: Intent): IBinder = {
    println("MyService is onBind invoked!")
    return myBinder
  }
  override def onCreate(): Unit = {
    println("MyService is onCreate invoke!")
    new Thread() {
      override def run: Unit = {
        while (loop) {
          Thread.sleep(1000)
          myBinder.count += 1
          println("count=" + myBinder.count)
        }
      }
    }.start()
    super.onCreate()
  }

  override def onStartCommand(intent: Intent, flags: Int, startId: Int) = {
    println("MyService is onStartCommand invoke!")
    super.onStartCommand(intent, flags, startId)
  }
  override def onDestroy(): Unit = {
    println("MyService is onDestroy invoke!")
    loop = false
    super.onDestroy()
  }
  override def onUnbind(intent: Intent) = {
    println("MyService is onUnbind invoke!")
    super.onUnbind(intent)
  }
}