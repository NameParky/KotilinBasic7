package com.example.myapplication

// Room 설정하는 법 두번째.
// 1. 다오를 임포트 해준다.
// 2. 각 CRUD를 어떻게 할 것 인지에 대한 내용을 추가한다.

// 참고사항
// Query는 셀렉트를 할때 사용하는 어노테이션이 아닌
// 사용자정의 쿼리를 짤 때 사용되는 어노테이션인 것 같다.
// select는 필터링과 정렬을 사용해야하므로 Query가 필수적이고
// 나머지 등록, 수정, 삭제는 기초적인 쿼리가 아니라면 Query로
// 설정을 해줄 수 있을 것 같다.

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    // 셀렉트문은 Query를 사용하여 지정을 해주어야한다.
    // @Select는 존재하지 않는다.
    @Query("SELECT * FROM user")
    fun getAllUser(): List<User>?

    // User라는 매개변수 타입의 매개변수 user를 바탕으로
    // 등록, 수정, 삭제를 진행하는 코드들.
    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}

// 다오에 대한 설정을 완료했다면 AppDatabase.kt를 설정하러간다.