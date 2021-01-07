package igor.kuridza.dice.githubapp.common

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import igor.kuridza.dice.githubapp.R
import igor.kuridza.dice.githubapp.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

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

fun Fragment.showSnackbar(message: String){
    Snackbar.make(this.requireView().rootView, message, Snackbar.LENGTH_SHORT).show()
}

fun <T> makeNetworkRequest(query: String,apiCall: suspend (query: String) -> Response<T>) : Flow<Resource<T>>{
    return flow {
        emit(Resource.Loading)
        try {
            val response: Response<T> = apiCall.invoke(query)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(Resource.Success(it))
                }
            }else
                emit(Resource.Error(response.message()))
        }catch (e: Throwable){
            emit(Resource.Error(e.message))
        }
    }
}

