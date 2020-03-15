package com.cuncis.pemesanantiketonline.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cuncis.pemesanantiketonline.R
import com.cuncis.pemesanantiketonline.data.model.MyTicket
import kotlinx.android.synthetic.main.item_my_ticket.view.*

class MyProfileAdapter(private val context: Context): RecyclerView.Adapter<MyProfileAdapter.MyProfileHolder>() {

    private var ticketList: List<MyTicket> = ArrayList()
    private lateinit var clickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileHolder {
        return MyProfileHolder(LayoutInflater.from(context).inflate(R.layout.item_my_ticket, parent, false))
    }

    override fun getItemCount(): Int = ticketList.size

    override fun onBindViewHolder(holder: MyProfileHolder, position: Int) {
        holder.bind(ticketList[position])
    }

    fun setTicketList(ticketList: List<MyTicket>) {
        this.ticketList = ticketList
        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    inner class MyProfileHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val namaWisata: TextView = view.tv_nama_wisata
        val lokasi: TextView = view.tv_lokasi
        val jumlahTiket: TextView = view.tv_jumlah_tiket

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(myTicket: MyTicket) {
            namaWisata.text = myTicket.nama_wisata
            lokasi.text = myTicket.lokasi
            jumlahTiket.text = myTicket.jumlah_tiket.toString()
        }

        override fun onClick(v: View?) {
            clickListener.onTicketClick(adapterPosition)
        }
    }

    interface ClickListener {
        fun onTicketClick(position: Int)
    }

}