package igor.kuridza.dice.githubapp.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.common.loadImage

object DataBindingAdapter {

    @BindingAdapter("imagePath")
    @JvmStatic fun loadImage(imageView: ImageView, imagePath: String){
        imageView.loadImage(imagePath)
    }

    @BindingAdapter("privateOrPublic")
    @JvmStatic fun setPrivateOrPublicIcon(imageView: ImageView, isPrivate: Boolean){
        if(isPrivate)
            imageView.setImageResource(R.drawable.ic_private)
        else
            imageView.setImageResource(R.drawable.ic_public)
    }
}