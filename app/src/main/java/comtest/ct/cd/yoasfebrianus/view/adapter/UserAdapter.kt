package comtest.ct.cd.yoasfebrianus.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import comtest.ct.cd.yoasfebrianus.R
import comtest.ct.cd.yoasfebrianus.util.image.loadImage
import comtest.ct.cd.yoasfebrianus.view.viewmodel.SortItem
import comtest.ct.cd.yoasfebrianus.view.viewmodel.UserModel

class UserAdapter(private val listener: CardListener): RecyclerView.Adapter<UserAdapter.Holder>() {


    private var itemList: List<UserModel> = mutableListOf()

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItem(listener, itemList[position])
    }

    fun clearAdapter() {
        itemList = mutableListOf()
        notifyDataSetChanged()
    }

    fun addFirstSetItems(dataList : List<UserModel>) {
        itemList = dataList
        notifyDataSetChanged()
    }

    fun insertList(dataList: List<UserModel>) {
        val dataTemp: MutableList<UserModel> = mutableListOf()
        dataTemp.addAll(itemList)
        dataTemp.addAll(dataList)
        itemList = dataTemp
        notifyDataSetChanged()
    }

    fun sortList(sortItem: SortItem) {
        when (sortItem.id) {
            SortItem.ID_SORT_ASC -> itemList = itemList.sortedBy { it.userName }
            SortItem.ID_SORT_DESC -> itemList = itemList.sortedByDescending { it.userName }
        }
        notifyDataSetChanged()
    }

    class Holder(v : View): RecyclerView.ViewHolder(v) {
        val itemImage = v.findViewById<ImageView>(R.id.item_image)
        val itemName = v.findViewById<TextView>(R.id.item_name)
        val itemCard = v.findViewById<CardView>(R.id.item_card)

        fun bindItem(listener: CardListener, userModel: UserModel) {
            itemImage.loadImage(userModel.imageUrl)
            itemName.text = userModel.userName
            itemCard.setOnClickListener {
                listener.onAdapterItemClicked(userModel)
            }
        }
    }


    interface CardListener {
        fun onAdapterItemClicked(userModel: UserModel)
    }

}