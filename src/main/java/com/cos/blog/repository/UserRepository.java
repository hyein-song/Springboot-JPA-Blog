package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


// DAO라고 생각하면 됨
// 자동으로 bean으로 등록이 된다. @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> { //User 테이블이 관리하는 reposotory이고 PK는 Integer 타입
    // JpaRepository가 findall이라는 함수를 들고있다. (다른것도 있음)
    // 이걸 사용하면 직접 만들지 않아도 여러 기능을 사용할 수 있다.


    //login을 위한 함수
    //JPA 네이밍 전략
    // Select * From user Where username=? and password=?
    // 아래처럼 이름을 쓰면 자동으로 위처럼 쿼리를 만들어줌
    User findByUsernameAndPassword(String username, String password);

    // 위와 같음
//    @Query(value = " Select * From user Where username=?1 And password=?2", nativeQuery = true)
//    User login(String username, String password);


}
