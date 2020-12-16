package igor.kuridza.dice.githubapp.common

import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import igor.kuridza.dice.githubapp.R

fun View.onClick(onClick: () -> Unit){
    this.setOnClickListener {
        onClick()
    }
}

fun ImageView.loadImage(imagePath: String){
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.apply {
        strokeWidth = 5F
        centerRadius = 50F
        start()
    }


    Glide.with(this)
        .load(imagePath)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_image_not_supported)
        .into(this)
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}