package com.example.android_servicetest

import android.view.View
import android.app.Activity

object CommonUtil {
  implicit def view2RichView(v: View) = {
    new {
      def onClick(handler: => Any) = {
        v.setOnClickListener(new View.OnClickListener() {
          override def onClick(view: View) = {
            handler
          }
        })
      }
    }
  }

  trait RichActivity extends Activity {
    def get[T <: View](id: Int): T = {
      findViewById(id).asInstanceOf[T]
    }
  }
}