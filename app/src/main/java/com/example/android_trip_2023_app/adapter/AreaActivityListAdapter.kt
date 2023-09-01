package com.example.android_trip_2023_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.ActivityResponse
import kotlin.math.ceil

class AreaActivityListAdapter(context: Context, private val dataList: List<ActivityResponse>) :
    ArrayAdapter<ActivityResponse>(context, R.layout.area_activity_list_item, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.area_activity_list_item, parent, false)

            // データを取得
            val data = dataList[position]

            val gridHeight = calculateGridViewHeight(data.activity_list.size)
            rowView.layoutParams.height = gridHeight

            // ViewHolderを初期化
            viewHolder = ViewHolder()
            viewHolder.textView = rowView.findViewById(R.id.area_title)
            viewHolder.gridView = rowView.findViewById(R.id.area_activity_grid)

            rowView.tag = viewHolder

            // データをテキストビューに表示
            viewHolder.textView.text = data.area_name

            // GridViewのアダプターを初期化し、データをバインド
            val gridAdapter = AreaActivityGridAdapter(context, data.activity_list)
            viewHolder.gridView.adapter = gridAdapter
        }
        return rowView!!
    }

    // ViewHolderパターンを使用してビューホルダーをキャッシュ
    private class ViewHolder {
        lateinit var textView: TextView
        lateinit var gridView: GridView // GridViewを追加
    }

    private fun calculateGridViewHeight(dataSize: Int): Int {
        val numRows = ceil((dataSize.toDouble() / 2))
        return (400 * numRows).toInt() + 200
    }
}
