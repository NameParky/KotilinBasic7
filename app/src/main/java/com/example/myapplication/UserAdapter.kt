package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(private val context: Context): RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    /*private lateinit var userList: List<User>*/
    private var userList: ArrayList<User> = ArrayList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nameText: TextView = itemView.findViewById(R.id.name_text)
        val ageText: TextView = itemView.findViewById(R.id.age_text)

    }

    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return MyViewHolder(view)
    }

    // 데이터 설정
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //데이터 변수에 담기
        var uId = userList[holder.adapterPosition].id
        var uName = userList[holder.adapterPosition].userName
        var uAge = userList[holder.adapterPosition].userAge

        // 데이터 적용
        /*holder.nameText.text = userList[holder.adapterPosition].userName
        holder.ageText.text = userList[holder.adapterPosition].userAge*/
        holder.nameText.text = uName
        holder.ageText.text=uAge

        //수정화면으로 이동
        holder.itemView.setOnClickListener {

            var intent: Intent = Intent(context, UpdateActivity::class.java)

            //값 담기
            intent.putExtra("uId", uId)
            intent.putExtra("uName", uName)
            intent.putExtra("uAge", uAge)
            context.startActivity(intent)
        }

    }

    // 아이템 갯수
    override fun getItemCount(): Int {
        return userList.size
    }

    // 사용자 등록
    fun setUserList(userList: ArrayList<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    // 사용자 삭제
    fun deleteUser(position: Int) {
        this.userList.removeAt(position)
    }
}