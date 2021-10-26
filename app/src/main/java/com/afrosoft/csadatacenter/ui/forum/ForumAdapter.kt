package com.afrosoft.csadatacenter.ui.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.databinding.SingleForumLayoutBinding
import com.squareup.picasso.Picasso

class ForumAdapter(val context: Context, var list: MutableList<Forum>): RecyclerView.Adapter<ForumAdapter.ForumHolder>() {

    inner class ForumHolder(val binding: SingleForumLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(forum: Forum) {
            binding.content.text = forum.content
            binding.title.text = forum.title
            binding.name.text = "${forum.farmer.first_name} ${forum.farmer.last_name}"
            binding.createdAt.text = forum.created_at

            Picasso.get().load("https://lyk.rkl.mybluehost.me/agro_aid/api/forums/${forum.picture}").centerCrop().fit().into(binding.picture)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumHolder {
        return ForumHolder(SingleForumLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ForumHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
    fun changeList(list: MutableList<Forum>) {
        this.list = list
        notifyDataSetChanged()
    }
}