package com.example.timer.adapters

import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.MainActivity
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
                val spannableText = SpannableString(tv_pagerRecycler_start.text)
                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(textView: View) {
                        ctx.skipOrFinish()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }

                spannableText.setSpan(clickableSpan, 0, tv_pagerRecycler_start.text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                tv_pagerRecycler_start.text = spannableText
                tv_pagerRecycler_start.movementMethod = LinkMovementMethod.getInstance()
            }
        }

    }

    override fun getItemCount(): Int = slides.size
}