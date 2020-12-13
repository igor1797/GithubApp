package igor.kuridza.dice.githubapp.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("total_count")
    val totalNumberOfUsers: Int,
    @SerializedName("items")
    val users: List<User>
)
