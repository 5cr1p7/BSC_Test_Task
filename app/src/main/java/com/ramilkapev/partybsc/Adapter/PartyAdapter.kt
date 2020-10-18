package com.ramilkapev.partybsc.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.ramilkapev.partybsc.Model.Users
import com.ramilkapev.partybsc.R
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL

class PartyAdapter(private val mUsers: ArrayList<Users>): RecyclerView.Adapter<PartyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: PartyAdapter.ViewHolder, position: Int) {
        // удаляем пользователя, пригласившего на вечеринку. Реализовано так, из-за строения json.
        // Думаю, можно сделать отдельный элемент id у каждого пользователя для удаления по нему,
        // но в данном случае это не реализовано
        mUsers.remove(Users(0, "Рамиль", "https://www.kleo.ru/img/articles/kak-izbavitsya-ot-depressii-3-1.png"))

        holder.bindUsers(mUsers[position])
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindUsers(user: Users) {
            val userPic: ShapeableImageView = itemView.findViewById(R.id.iv_user_pic)
            val userName: TextView = itemView.findViewById(R.id.tv_user_name)
                // без библиотеки на получилось обойтись,
                // метод, использованный в main, здесь использовать не удалось
                Glide.with(itemView).load(user.picture).into(userPic)

                userName.text = user.name
        }
    }
}