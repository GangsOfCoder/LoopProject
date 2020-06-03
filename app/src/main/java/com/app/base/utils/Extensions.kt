package com.app.base.utils

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}


fun <T> androidLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)


fun ImageView.loadImg(url: String, activity: Context) {
//    GlideApp.with(activity)
//            .load(url)
//            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//            .thumbnail(0.1f)
//            .placeholder(R.drawable.ic_user_profile_holder)
//            .dontAnimate()
//            .into(this)
}
val Context.appContext: Application
    get() = applicationContext as Application
