package comtest.ct.cd.yoasfebrianus.view.viewmodel

data class UserListModel (
    val dataList: List<UserModel> = mutableListOf(),
    val hasNextData: Boolean = false
)