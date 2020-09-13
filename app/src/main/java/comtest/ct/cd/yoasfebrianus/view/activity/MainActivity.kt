package comtest.ct.cd.yoasfebrianus.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import comtest.ct.cd.yoasfebrianus.R
import comtest.ct.cd.yoasfebrianus.di.DaggerUserComponent
import comtest.ct.cd.yoasfebrianus.util.popup.showSnackbar
import comtest.ct.cd.yoasfebrianus.view.adapter.BottomSheetAdapter
import comtest.ct.cd.yoasfebrianus.view.adapter.UserAdapter
import comtest.ct.cd.yoasfebrianus.view.presenter.UserViewModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.SortItem
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserListModel
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserModel
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),
    CoroutineScope,
    UserAdapter.CardListener,
    BottomSheetAdapter.BottomSheetSortClickListener {

    private val masterJob = SupervisorJob()
    override val coroutineContext: CoroutineContext = masterJob + Dispatchers.Main

    lateinit var recyclerView: RecyclerView
    lateinit var etSearch: EditText
    lateinit var sortMenu : ImageView
    lateinit var rootLayout : ConstraintLayout

    @Inject
    lateinit var viewModel: UserViewModel

    lateinit var adapter: UserAdapter

    var sortItemList: List<SortItem> = mutableListOf()

    lateinit var sortBottomSheet: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInjector()
        bindUi()
        initViewModelResp()
    }

    override fun onBottomSheetSortItemClicked(sortItem: SortItem) {
        if (sortBottomSheet.isShowing) {
            sortBottomSheet.hide()
        }
        sortItemList = getItemBasedOnSelected(sortItem)
        adapter.sortList(sortItem)
    }

    override fun onAdapterItemClicked(userModel: UserModel) {

    }

    private fun callData(isFirstTime: Boolean, keyword: String) {
        if (keyword.isEmpty()) return
        if (isFirstTime) {
            viewModel.getUserData(keyword)
        } else {
            viewModel.getUserDataMore(keyword)
        }
    }

    private fun bindUi() {
        recyclerView = findViewById(R.id.recycler_view)
        etSearch = findViewById(R.id.et_search)
        sortMenu = findViewById(R.id.btn_menu)
        rootLayout = findViewById(R.id.root_layout)

        sortItemList = getDefaultSortItem()
        adapter = UserAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val watcher = object: TextWatcher {
            private var keyword = ""
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val query = p0.toString().trim()
                if (keyword == query) return
                keyword = query
                launch {
                    delay(500)
                    if (keyword != query) return@launch
                    callData(true, keyword)
                }
            }
        }
        etSearch.addTextChangedListener(watcher)

        sortMenu.setOnClickListener{
            showBottomSheetSort()
        }
    }


    private fun initInjector() {
        DaggerUserComponent.builder()
            .build()
            .inject(this)
    }

    private fun initViewModelResp() {
        viewModel.run {
            userDataResp.observe(this@MainActivity, Observer {
                if(it.isSuccess) {
                    populateDataFirstTime(it.getOrDefault(UserListModel()))
                } else {
                    showErrorLoadFirstTime(Exception(it.exceptionOrNull()))
                }
            })
        }
    }

    private fun populateDataFirstTime(data: UserListModel) {
        sortItemList = getDefaultSortItem()
        adapter.addFirstSetItems(dataList = data.dataList)
        recyclerView.smoothScrollToPosition(0)
    }

    private fun showErrorLoadFirstTime(e: Exception) {
        rootLayout.showSnackbar(e.localizedMessage)
    }

    private fun populateDataMore(data: UserListModel) {

    }

    private fun showBottomSheetSort() {
        sortBottomSheet = BottomSheetDialog(this)
        sortBottomSheet.setContentView(R.layout.layout_bottomsheet_sort)
        val recyclerView = sortBottomSheet.findViewById<RecyclerView>(R.id.rv_sort)
        val sortAdapter = BottomSheetAdapter(this)
        recyclerView?.adapter = sortAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
        sortAdapter.bindSortItem(sortItemList)

        sortBottomSheet.show()
    }

    fun getDefaultSortItem(): List<SortItem> {
        val itemList: MutableList<SortItem> = mutableListOf()
        itemList.add(SortItem(SortItem.ID_SORT_ASC, SortItem.KEY_SORT_ASC, false))
        itemList.add(SortItem(SortItem.ID_SORT_DESC, SortItem.KEY_SORT_DESC, false))
        itemList.add(SortItem(SortItem.ID_SORT_NONE, SortItem.KEY_SORT_NONE, true))
        return itemList
    }

    fun getItemBasedOnSelected(sortItem: SortItem): List<SortItem> {
        val itemList: MutableList<SortItem> = mutableListOf()
        if (sortItem.id == SortItem.ID_SORT_ASC && sortItem.checked) {
            itemList.add(SortItem(SortItem.ID_SORT_ASC, SortItem.KEY_SORT_ASC, true))
            itemList.add(SortItem(SortItem.ID_SORT_DESC, SortItem.KEY_SORT_DESC, false))
            itemList.add(SortItem(SortItem.ID_SORT_NONE, SortItem.KEY_SORT_NONE, false))
        } else if (sortItem.id == SortItem.ID_SORT_DESC && sortItem.checked) {
            itemList.add(SortItem(SortItem.ID_SORT_ASC, SortItem.KEY_SORT_ASC, false))
            itemList.add(SortItem(SortItem.ID_SORT_DESC, SortItem.KEY_SORT_DESC, true))
            itemList.add(SortItem(SortItem.ID_SORT_NONE, SortItem.KEY_SORT_NONE, false))
        } else {
            itemList.addAll(getDefaultSortItem())
        }
        return itemList
    }
}