package com.example.android_trip_2023_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.QuestResponse
import com.example.android_trip_2023_app.view_model.QuestViewModel

class AreaQuestGridAdapter(
    private val context: Context,
    private val data: QuestResponse,
    private val viewModel: QuestViewModel
    ) : BaseAdapter() {

    override fun getCount(): Int {
        return data.questions.size
    }

    override fun getItem(position: Int): Any {
        return data.questions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val gridViewItem = inflater.inflate(R.layout.area_activity_grid_item, null)

        val activityTitle = gridViewItem.findViewById<TextView>(R.id.activity_title)
        val activityPoint = gridViewItem.findViewById<TextView>(R.id.activity_point)

        // itemListからデータを取得してTextViewに設定
        val item = data.questions[position]

        gridViewItem.setOnClickListener {
            viewModel.dispatchTakePictureIntent(item, data.areaName)
        }

        activityTitle.text = item.questionName
        var pointStr = ""
        pointStr = if (item.point != null) {
            "${item.point} pt"
        } else {
            "AI採点"
        }
        activityPoint.text = pointStr

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        // Gridアイテムの高さを指定
        val itemWidth = screenWidth * 2 / 5
        gridViewItem.layoutParams = AbsListView.LayoutParams(itemWidth, itemWidth)

        return gridViewItem
    }
}
