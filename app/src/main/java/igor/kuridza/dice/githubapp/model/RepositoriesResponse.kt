package igor.kuridza.dice.githubapp.model

import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    @SerializedName("total_count")
    val totalNumberOfRepositories: Int,
    @SerializedName("items")
    val repositories: List<Repository>
)
