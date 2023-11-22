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
import com.bumptech.glide.Glide
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.ContributionResponse
import com.example.android_trip_2023_app.model.TeamDatabaseModel
import com.example.android_trip_2023_app.utils.DatabaseHelper

class ContributionListAdapter(context: Context, private val dataList: List<ContributionResponse>) :
    ArrayAdapter<ContributionResponse>(context, R.layout.contribution_list_item, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.contribution_list_item, parent, false)

            // ViewHolderを初期化
            viewHolder = ViewHolder()
            viewHolder.teamNameTextView = rowView.findViewById(R.id.team_name)
            viewHolder.contributionImageView = rowView.findViewById(R.id.contribution_image)
            viewHolder.iconImageView = rowView.findViewById(R.id.team_icon_contribution_list)
            viewHolder.pointTextView = rowView.findViewById(R.id.contribution_point)
            viewHolder.contributionAreaTextView = rowView.findViewById(R.id.contribution_area)

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
            viewHolder.iconImageView.background = circleDrawable
        }

        var pointStr = String.format("%.1f", data.point)
        pointStr = "${pointStr}pt"
        viewHolder.pointTextView.text = pointStr

        Glide.with(context)
            .load(data.cdn)
            .into(viewHolder.contributionImageView)

        if(data.areaName != null) {
            viewHolder.contributionAreaTextView.text = "# ${data.areaName}"
        }

        return rowView!!
    }
}

// ViewHolderパターンを使用してビューホルダーをキャッシュ
private class ViewHolder {
    lateinit var teamNameTextView: TextView
    lateinit var contributionImageView: ImageView
    lateinit var iconImageView: ImageView
    lateinit var pointTextView: TextView
    lateinit var contributionAreaTextView: TextView
}

private fun getTeamData(context: Context): List<TeamDatabaseModel> {
    return DatabaseHelper(context).getTeamDataList() ?: emptyList()
}
