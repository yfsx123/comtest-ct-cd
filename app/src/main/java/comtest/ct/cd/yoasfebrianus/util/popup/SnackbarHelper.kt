package comtest.ct.cd.yoasfebrianus.util.popup

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(errorMessage: String) {
    Snackbar.make(this, errorMessage, Snackbar.LENGTH_SHORT).show()
}