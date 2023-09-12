package com.example.android_trip_2023_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getString
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.ActivityResponse
import com.example.android_trip_2023_app.model.TeamResponse
import kotlin.math.ceil

class TeamListAdapter(context: Context, private val dataList: List<TeamResponse>) :
    ArrayAdapter<TeamResponse>(context, R.layout.team_list_item, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.team_list_item, parent, false)

            // データを取得
            val data = dataList[position]

            // ViewHolderを初期化
            viewHolder = ViewHolder()
            viewHolder.teamNameTextView = rowView.findViewById(R.id.team_name)
            viewHolder.teamPointTextView = rowView.findViewById(R.id.team_point)

            rowView.tag = viewHolder

            // データをテキストビューに表示
            viewHolder.teamNameTextView.text = data.team_name
            val pointStr = "${data.point} pt"
            viewHolder.teamPointTextView.text = pointStr
        }
        return rowView!!
    }

    // ViewHolderパターンを使用してビューホルダーをキャッシュ
    private class ViewHolder {
        lateinit var teamNameTextView: TextView
        lateinit var teamPointTextView: TextView
    }
}
