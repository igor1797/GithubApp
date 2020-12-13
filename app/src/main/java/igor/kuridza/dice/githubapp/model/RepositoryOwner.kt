package igor.kuridza.dice.githubapp.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwner(
    @SerializedName("login")
    val ownerName: String
)
