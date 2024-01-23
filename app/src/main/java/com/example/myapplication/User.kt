package com.example.myapplication

// Room 설정하는법 첫번째.
// 1. 엔티티를 임포트해준다.
// 2. 데이터클래스에 어노테이션 Entity를 활용하여 테이블명을 설정해준다.
// 3. 기본키를 포함한 컬럼들을 나열해준다.

//@Entity를 사용하여 자동완성 가능.
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(

    // 기본키를 설정한다.
    // autoGenerate를 true로하면 기본키를 자동으로 생성해준다.
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    // 컬럼인포를 사용해서 컬럼을 생성해주는 코드.
    // UserName이라는 String값을 받는다.
    // 오라클에서 varchar2를 설정했던 것과 비슷한 느낌으로 보면 될 것 같다.
    @ColumnInfo(name = "userName")
    val userName:String?,

    // 이하 컬럼내용 위와 동일.
    @ColumnInfo(name = "userAge")
    val userAge : String?
)

// 테이블에대한 내용을 작성완료했으면 Interface로 Dao를 만들러간다.
