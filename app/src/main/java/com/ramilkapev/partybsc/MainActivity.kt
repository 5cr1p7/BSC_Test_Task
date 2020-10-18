package com.ramilkapev.partybsc

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.imageview.ShapeableImageView
import com.ramilkapev.partybsc.Adapter.PartyAdapter
import com.ramilkapev.partybsc.Model.Party
import com.ramilkapev.partybsc.Model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var partyAdapter: PartyAdapter? = null
    private var usersList: ArrayList<Users>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val partyName: TextView = findViewById(R.id.tv_party_name)
        val partyPicture: ImageView = findViewById(R.id.iv_party_picture)
        val userInvitedName: TextView = findViewById(R.id.tv_user_invited_name)
        val userInvitedPicture: ShapeableImageView = findViewById(R.id.iv_user_invited_pic)
        val recyclerView: RecyclerView = findViewById(R.id.rv_users_list)
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            // Some navigation
        }

        val json = assets.open("party.json").bufferedReader().readText()    // читаем json файл из ассетов
        val party = Json.decodeFromString<Party>(json)  // десериализируем из строки в объект (дата класс) Party

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        usersList = party.users as ArrayList<Users>
        partyAdapter = PartyAdapter(usersList as ArrayList<Users>)
        recyclerView.adapter = partyAdapter

        partyName.text = party.partyName    // берем название вечеринки из json
        userInvitedName.text = party.users[0].name  // берем имя пригласившего из json

        GlobalScope.launch(Dispatchers.IO) {    // асинронное скачивание изображений по url из json в imageView
            val partyPic = downloadImage(party.partyPicture)
            val userInvitedPic = downloadImage(party.users[0].picture)

            runOnUiThread {
                partyPicture.setImageBitmap(partyPic)
                userInvitedPicture.setImageBitmap(userInvitedPic)
            }
        }
    }

    private fun downloadImage(url: String): Bitmap { // функция для скачивания изображений
        val picture = URL(url)
        return BitmapFactory.decodeStream(picture.openConnection().getInputStream())
    }
}