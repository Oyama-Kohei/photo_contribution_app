package com.example.android_trip_2023_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.android_trip_2023_app.R
import com.example.android_trip_2023_app.model.ContributionGetResponse

class ContributionListAdapter(context: Context, private val dataList: List<ContributionGetResponse>) :
    ArrayAdapter<ContributionGetResponse>(context, R.layout.contribution_list_item, dataList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.contribution_list_item, parent, false)

            // データを取得
            val data = dataList[position]

            // ViewHolderを初期化
            viewHolder = ViewHolder()
            viewHolder.teamNameTextView = rowView.findViewById(R.id.team_name)
            viewHolder.contributionImageView = rowView.findViewById(R.id.contribution_image)
            viewHolder.iconImageView = rowView.findViewById(R.id.icon_image)
            viewHolder.pointTextView = rowView.findViewById(R.id.contribution_point)

            rowView.tag = viewHolder

            viewHolder.teamNameTextView.text = data.team_name

            val pointStr = "${data.contribution_point}pt"
            viewHolder.pointTextView.text = pointStr

            Glide.with(context)
                .load(data.image_url)
                .into(viewHolder.contributionImageView)

            Glide.with(context)
                .load(data.icon)
                .into(viewHolder.iconImageView)
        }
        return rowView!!
    }

    // ViewHolderパターンを使用してビューホルダーをキャッシュ
    private class ViewHolder {
        lateinit var teamNameTextView: TextView
        lateinit var contributionImageView: ImageView
        lateinit var iconImageView: ImageView
        lateinit var pointTextView: TextView
    }
}
