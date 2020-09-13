package comtest.ct.cd.yoasfebrianus.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import comtest.ct.cd.yoasfebrianus.R
import comtest.ct.cd.yoasfebrianus.view.viewmodel.SortItem
import kotlinx.android.synthetic.main.item_bottomsheet_sort.view.*

class BottomSheetAdapter(val listener: BottomSheetSortClickListener): RecyclerView.Adapter<BottomSheetAdapter.Holder>() {

    private var sortItemList: List<SortItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_bottomsheet_sort, parent, false))
    }

    override fun getItemCount(): Int {
        return sortItemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItem(listener, sortItemList[position])
    }

    fun bindSortItem(itemList: List<SortItem>) {
        sortItemList = itemList
        notifyDataSetChanged()
    }

    class Holder(val v: View): RecyclerView.ViewHolder(v) {
        val sortCard = v.findViewById<CardView>(R.id.sort_card)
        val sortText = v.findViewById<TextView>(R.id.sort_text)
        val sortCheck = v.findViewById<ImageView>(R.id.sort_check)

        fun bindItem(listener: BottomSheetSortClickListener, sortItem: SortItem) {
            sortText.text = sortItem.text
            if (sortItem.checked) {
                sortCheck.setImageDrawable(v.context.getDrawable(R.drawable.ic_baseline_check_24))
            } else {
                sortCheck.setImageDrawable(null)
            }
            sortCard.setOnClickListener {
                listener.onBottomSheetSortItemClicked(SortItem(id = sortItem.id, text = sortItem.text, checked = true))
            }
        }
    }

    interface BottomSheetSortClickListener {
        fun onBottomSheetSortItemClicked(sortItem: SortItem)
    }
}