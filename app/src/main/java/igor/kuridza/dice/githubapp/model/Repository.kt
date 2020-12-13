package igor.kuridza.dice.githubapp.model

import com.google.gson.annotations.SerializedName

data class Repository(
    val name: String,
    val owner: RepositoryOwner,
    val private: Boolean,
    @SerializedName("open_issues_count")
    val numberOfOpenedIssues: Int,
    @SerializedName("watchers_count")
    val numberOfWatchers: Int,
    @SerializedName("stargazers_count")
    val numberOfStarGazers: Int,
    @SerializedName("forks_count")
    val numberOfForks: Int,
    val description: String?
)
