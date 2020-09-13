package comtest.ct.cd.yoasfebrianus.util.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import comtest.ct.cd.yoasfebrianus.R

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(R.drawable.ic_baseline_person_pin_24)
        .centerCrop()
        .into(this)
}