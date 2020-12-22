package igor.kuridza.dice.githubapp.model

sealed class Resource{
    data class Success<T>(val data: T) : Resource()
    data class Error(val message: String?) : Resource()
    object Loading: Resource()
}
