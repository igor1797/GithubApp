package igor.kuridza.dice.githubapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryOwner(
    @SerializedName("login")
    val ownerName: String,
    @SerializedName("html_url")
    val ownerUrl: String
): Parcelable
