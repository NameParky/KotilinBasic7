package com.example.myapplication

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    private var userList: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val insertBtn: FloatingActionButton = findViewById(R.id.insertBtn)
        insertBtn.setOnClickListener {
            val intent: Intent = Intent(this, InsertActivity::class.java)

            activityResult.launch(intent)
        }

        // RecycleView 설정
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // UserAdapter 초기화
        adapter = UserAdapter(this)

        // Adapter 적용
        recyclerView.adapter = adapter

        // 사용자 조회
        loadUserList()

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                var position: Int = viewHolder.bindingAdapterPosition

                when(direction) {
                    ItemTouchHelper.LEFT -> {
                        var uId: Int? = userList.get(position).id
                        var uName: String? = userList.get(position).userName
                        var uAge: String? = userList.get(position).userAge

                        var user: User = User(uId, uName, uAge)

                        // 사용자 삭제
                        adapter.deleteUser(position)

                        // 사용자 삭제 화면 재정리
                        adapter.notifyItemRemoved(position)

                        if(userList.size == 0) {
                            Toast.makeText(
                                applicationContext, "등록된 사용자가 없습니다",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // db생성
                        var db: AppDatabase? = AppDatabase.getDatabase(applicationContext)

                        // 삭제 쿼리
                        db?.userDao()?.deleteUser(user)
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                // 스와이프 기능
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
                dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("삭제")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(recyclerView) // recyclerView에 스와이프 적용.
    }

    val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        // InsertActivity의 상태값은 무조건 OK로 오게된는데,
        // 일단 상태값을 넘겨서 함수를 실행시킨 이후에,
        // 해당 함수에서 null 여부에 따른 로직을 실행하게 하는 것 같다.
        // 이 때, 상태값을 공용으로 넣고 로직에서 분리하는게 일반적인 것인가.
        // 아니면 상태값을 따로 넘겨 다른 함수로 들어가게 하는것이 일반적인 것인가.
        if(it.resultCode == RESULT_OK) {
            loadUserList()
        }
    }

    // 액티비티가 백그라운드에 있는데 호출되면 실행
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // 사용자 조회
        loadUserList()
    }

    // 사용자 조회
    private fun loadUserList() {

        val db: AppDatabase? = AppDatabase.getDatabase(applicationContext)

        userList = db?.userDao()?.getAllUser() as ArrayList<User>

        if(!userList.isNullOrEmpty()) {

            // 데이터 적용
            adapter.setUserList(userList)

            /*val position: Int = userList.size - 1

            // Toast는 메세지를 간단하게 표시함.
            Toast.makeText(
                this, "최근 등록자 : " + userList[position].userName,
                Toast.LENGTH_SHORT
            ).show()*/

        } else {

            Toast.makeText(
                this, "등록된 사용자가 없습니다",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}