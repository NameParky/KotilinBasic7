package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // 초기화
        var upNameEdit: EditText = findViewById(R.id.up_name_edit)
        var upAgeEdit: EditText = findViewById(R.id.up_age_edit)
        var updateBtn: Button = findViewById(R.id.update_btn)

        // UserAdapter에서 넘어온 데이터 변수에 담기
        var uId: Int = intent.getIntExtra("uId", 0)
        var uName: String? = intent.getStringExtra("uName")
        var uAge: String? = intent.getStringExtra("uAge")

        // 화면에 값 적용
        upNameEdit.setText(uName)
        upAgeEdit.setText(uAge)

        // 수정버튼 이벤트
        updateBtn.setOnClickListener {

            // 입력값을 변수에 담는다.
            var iName = upNameEdit.text.toString()
            var iAge = upAgeEdit.text.toString()

            // 사용자 클래스 생성
            var user: User = User(uId, iName, iAge)

            // db생성
            var db: AppDatabase? = AppDatabase.getDatabase(applicationContext)

            // 데이터 수정하기
            db?.userDao()?.updateUser(user)

            // 메인화면으로 이동
            var intent: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)

            // 액티비티 종료
            finish()

        }

    }
}