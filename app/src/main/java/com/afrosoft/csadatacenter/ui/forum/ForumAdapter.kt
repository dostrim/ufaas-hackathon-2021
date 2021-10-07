package com.afrosoft.csadatacenter.ui.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.SingleForumLayoutBinding

class ForumAdapter(val context: Context, val list: MutableList<Forum>): RecyclerView.Adapter<ForumAdapter.ForumHolder>() {

    inner class ForumHolder(val binding: SingleForumLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(forum: Forum) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumHolder {
        return ForumHolder(SingleForumLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ForumHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
}