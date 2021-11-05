package com.example.timer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.activities.MainActivity
import com.example.timer.R
import com.example.timer.data.SlideData
import kotlinx.android.synthetic.main.column_item.view.*


class ViewPagerAdapter(private val slides: List<SlideData>, val ctx: MainActivity): RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {
    class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.column_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val slide = slides[position]

        holder.itemView.apply {
            tv_pagerRecycler_title.text = slide.title
            iv_pagerRecycler_image.setImageResource(slide.image)
            tv_pagerRecycler_describe.text = slide.description

            tv_pagerRecycler_skip.setOnClickListener {
                ctx.skipOrFinish()
            }
            if(position == slides.size - 1){
                tv_pagerRecycler_start.visibility = View.VISIBLE
                tv_pagerRecycler_skip.visibility = View.INVISIBLE
                tv_pagerRecycler_start.setOnClickListener {
                    ctx.skipOrFinish()
                }
            }
        }

    }

    override fun getItemCount(): Int = slides.size
}