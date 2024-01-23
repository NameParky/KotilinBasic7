package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        val nameEdit: EditText = findViewById(R.id.name_edit)
        val ageEdit: EditText = findViewById(R.id.age_edit)
        val saveBtn: Button = findViewById(R.id.save_btn)

        saveBtn.setOnClickListener {
            val sName = nameEdit.text.toString()
            val sAge = ageEdit.text.toString()

            // 사용자 등록
            insertUser(sName, sAge)
        }
    }
    private fun insertUser(name: String, age: String) {

        // User클래스의 프라이머리키로 등록한 id(없을경우 자동생성)와
        // 이름과 나이를 user에 대입.
        val user = User(null, name, age)

        // AppDatabase가 null일 경우를 위한 널 안전.
        var db: AppDatabase? = AppDatabase.getDatabase(applicationContext)

        // db가 null이 아닌 경우에만 insertUser 호출
        db?.userDao()?.insertUser(user)

        // 상태값을 돌려준다.
        setResult(Activity.RESULT_OK)
        // 예시에는 이렇게나와있지만, 만약 db가 null이어서
        // insertUser가 실행이되지 않았다면
        // RESULT_OK는 잘못된 코드아닌가??.


        // 액티비티 닫기.
        finish()
    }
}