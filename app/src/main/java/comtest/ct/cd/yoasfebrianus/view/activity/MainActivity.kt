package comtest.ct.cd.yoasfebrianus.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import comtest.ct.cd.yoasfebrianus.R
import comtest.ct.cd.yoasfebrianus.di.DaggerUserComponent

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var etSearch: EditText
    lateinit var sortMenu : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInjector()
        bindUi()
    }

    private fun bindUi() {
        recyclerView = findViewById(R.id.recycler_view)
        etSearch = findViewById(R.id.et_search)
        sortMenu = findViewById(R.id.btn_menu)
    }

    private fun initInjector() {
        DaggerUserComponent.builder()
            .build()
            .inject(this)
    }
}