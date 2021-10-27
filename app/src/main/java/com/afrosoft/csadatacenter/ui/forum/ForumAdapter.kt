package com.afrosoft.csadatacenter.ui.forum

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afrosoft.csadatacenter.MainActivity
import com.afrosoft.csadatacenter.databinding.SingleForumLayoutBinding
import com.afrosoft.csadatacenter.models.UserResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog

class ForumAdapter(val context: Context, var list: MutableList<Forum>, var func:()->Unit): RecyclerView.Adapter<ForumAdapter.ForumHolder>() {

    val spotDialog = SpotsDialog.Builder().setContext(context).build()
    private val TAG = "ForumAdapter"

    inner class ForumHolder(val binding: SingleForumLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(forum: Forum) {
            binding.content.text = forum.content
            binding.title.text = forum.title
            binding.name.text = "${forum.farmer.first_name} ${forum.farmer.last_name}"
            binding.createdAt.text = forum.created_at
            binding.upVote.text = "Up Vote (${forum.up_vote})"
            binding.downVote.text = "Down Vote (${forum.down_vote})"

            Picasso.get().load("https://lyk.rkl.mybluehost.me/agro_aid/api/forums/${forum.picture}").centerCrop().fit().into(binding.picture)

            binding.upVote.setOnClickListener {
                vote(forum,"up")
            }
            binding.downVote.setOnClickListener {
                vote(forum,"down")
            }


        }
    }

    private fun vote(forum: Forum, up_down: String) {

        spotDialog?.show()
        AndroidNetworking.post("https://lyk.rkl.mybluehost.me/agro_aid/api/forums_vote.php")
            .addBodyParameter("type",up_down)
            .addBodyParameter("id",forum.id.toString())
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String?) {
                    spotDialog?.dismiss()

                    val nrf = Gson().fromJson(response, UserResponse::class.java)
                    if (nrf.status_code == 200){
                        //action
                        func.invoke()
                    }

                    Toast.makeText(context, nrf.status_message, Toast.LENGTH_SHORT).show()

                }

                override fun onError(anError: ANError?) {
                    spotDialog?.dismiss()

                    Log.d(TAG, "onError: ${anError?.errorCode}")
                    Log.d(TAG, "onError: ${anError?.errorBody}")
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                }
            })
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