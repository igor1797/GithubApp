package igor.kuridza.dice.githubapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val name: String,
    val owner: User,
    val private: Boolean,
    @SerializedName("open_issues_count")
    val numberOfOpenedIssues: Int,
    @SerializedName("watchers_count")
    val numberOfWatchers: Int,
    @SerializedName("stargazers_count")
    val numberOfStarGazers: Int,
    @SerializedName("forks_count")
    val numberOfForks: Int,
    val description: String?,
    @SerializedName("html_url")
    val repositoryUrl: String
):Parcelable
