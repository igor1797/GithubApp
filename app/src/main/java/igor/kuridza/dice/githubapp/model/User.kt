package igor.kuridza.dice.githubapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
   @SerializedName("login")
   val name: String,
   @SerializedName("avatar_url")
   val avatarImagePath: String,
   @SerializedName("html_url")
   val profileUrl: String
):Parcelable
