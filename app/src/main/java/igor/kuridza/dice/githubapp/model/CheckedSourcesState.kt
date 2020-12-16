package igor.kuridza.dice.githubapp.model

data class CheckedSourcesState(
    val isRepositorySourceChecked: Boolean = true,
    val isUsersSourceChecked: Boolean = false
){
    fun getNumberOfCheckedSources(): Int{
        var numberOfCheckedSources = 0
        if(isRepositorySourceChecked)
            numberOfCheckedSources++
        if(isUsersSourceChecked)
            numberOfCheckedSources++
        return numberOfCheckedSources
    }
}
