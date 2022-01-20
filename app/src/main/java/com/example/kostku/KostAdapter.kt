package com.example.kostku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.room.Kost
import kotlinx.android.synthetic.main.list_kost.view.*
import java.util.ArrayList

class KostAdapter(private val kostn: ArrayList<Kost>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<KostAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_kost, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val kost  = kostn[position]
        holder.view.text_title.text = kost.title
        holder.view.text_title.setOnClickListener {
            listener.onClick( kost )
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate( kost )
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete( kost )
        }
    }

    override fun getItemCount() = kostn.size

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Kost>){
        kostn.clear()
        kostn.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(kost: Kost)
        fun onUpdate(kost: Kost)
        fun onDelete(kost: Kost)
    }
}