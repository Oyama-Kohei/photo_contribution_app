package com.example.android_trip_2023_app.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.QuestResponse
import com.example.android_trip_2023_app.view_model.QuestViewModel
import kotlin.math.ceil

class AreaQuestListAdapter(
    context: Context,
    private val dataList: List<QuestResponse>,
    private val viewModel: QuestViewModel
) :
    ArrayAdapter<QuestResponse>(context, R.layout.area_activity_list_item, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.area_activity_list_item, parent, false)

            // ViewHolderを初期化
            viewHolder = ViewHolder()
            viewHolder.areaText = rowView.findViewById(R.id.area_title)
            viewHolder.activityGrid = rowView.findViewById(R.id.area_activity_grid)

            rowView.tag = viewHolder
        } else {
            // 再利用の場合、ViewHolder を取得
            viewHolder = rowView.tag as ViewHolder
        }
        // データを取得
        val data = dataList[position]

        val gridHeight = calculateGridViewHeight((data.questions.size).toDouble())
        rowView!!.layoutParams.height = gridHeight

        // データをテキストビューに表示
        viewHolder.areaText.text = data.areaName

        // GridViewのアダプターを初期化し、データをバインド
        val gridAdapter = AreaQuestGridAdapter(context, data, viewModel)
        viewHolder.activityGrid.adapter = gridAdapter

        return rowView
    }

    // ViewHolderパターンを使用してビューホルダーをキャッシュ
    private class ViewHolder {
        lateinit var areaText: TextView
        lateinit var activityGrid: GridView // GridViewを追加
    }

    private fun calculateGridViewHeight(dataSize: Double): Int {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        // Gridアイテムの高さを指定
        val itemHeight = screenWidth * 2 / 5
        val numRows = ceil((dataSize / 2))
        return (itemHeight * numRows).toInt() + (itemHeight / 7 * numRows).toInt() + 200
    }
}
