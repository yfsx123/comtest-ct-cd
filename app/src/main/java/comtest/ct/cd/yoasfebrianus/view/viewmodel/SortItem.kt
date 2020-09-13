package comtest.ct.cd.yoasfebrianus.view.viewmodel

data class SortItem(
    val id: Int = 0,
    val text: String = "",
    val checked: Boolean = false
) {
    companion object {
        const val ID_SORT_ASC = 0
        const val ID_SORT_DESC = 1
        const val ID_SORT_NONE = 2
        const val KEY_SORT_ASC = "ascending"
        const val KEY_SORT_DESC = "descending"
        const val KEY_SORT_NONE = "none"
    }

}
