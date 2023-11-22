package com.example.android_trip_2023_app.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.PointSummaryResponse
import com.example.android_trip_2023_app.model.TeamDatabaseModel
import com.example.android_trip_2023_app.utils.DatabaseHelper

class TeamListAdapter(context: Context, private val dataList: List<PointSummaryResponse>) :
    ArrayAdapter<PointSummaryResponse>(context, R.layout.team_list_item, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.team_list_item, parent, false)

            // ViewHolderを初期化
            viewHolder = ViewHolder()
            viewHolder.teamIconImageView = rowView.findViewById(R.id.team_icon_team_list)
            viewHolder.teamNameTextView = rowView.findViewById(R.id.team_name)
            viewHolder.teamPointTextView = rowView.findViewById(R.id.team_point)

            rowView.tag = viewHolder
        } else {
            // 再利用の場合、ViewHolder を取得
            viewHolder = rowView.tag as ViewHolder
        }

        // データを取得
        val data = dataList[position]
        val teamData = getTeamData(context).filter{it.teamId == data.teamId}
        val circleDrawable = ContextCompat.getDrawable(context, R.drawable.team_icon_oval) as GradientDrawable
        if(teamData.isNotEmpty()) {
            circleDrawable.setColor(Color.parseColor(teamData.first().teamColor))
            viewHolder.teamNameTextView.text = teamData.first().teamName
            viewHolder.teamIconImageView.background = circleDrawable
        }

        var pointStr = String.format("%.1f", data.point)
        pointStr = "${pointStr}pt"
        viewHolder.teamPointTextView.text = pointStr
        return rowView!!
    }

    // ViewHolderパターンを使用してビューホルダーをキャッシュ
    private class ViewHolder {
        lateinit var teamIconImageView: ImageView
        lateinit var teamNameTextView: TextView
        lateinit var teamPointTextView: TextView
    }


    private fun getTeamData(context: Context): List<TeamDatabaseModel> {
        return DatabaseHelper(context).getTeamDataList() ?: emptyList()
    }
}
