package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;


// ORM : 언어 object를 테이블로 매핑해주는 기술

//@DynamicInsert // insert할때 null인 필드를 제외시켜준다.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // 테이블화
public class User {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라감
    private int id; // auto_increment

    @Column(nullable = false, length = 100, unique = true)
    private String username;
    
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

//    @ColumnDefault(" 'user' ")
//    private String role; // 나중엔 Enum을 사용하는게 좋다.

    // DB는 RoleType이 없으니 아래 annotation을 붙여서 string이라는것을 알려줘야 한다.
    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String oauth; // kakao,google...

    @CreationTimestamp //  시간이 자동으로 입력됨
    private Timestamp createDate;


}
