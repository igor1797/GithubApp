package igor.kuridza.dice.githubapp.common

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.onClick(onClick: () -> Unit){
    this.setOnClickListener {
        onClick()
    }
}

fun ImageView.loadImage(imagePath: String){
    Glide.with(this).load(imagePath).into(this)
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}