package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


// ORM : 언어 object를 테이블로 매핑해주는 기술
@Entity // 테이블화
public class User {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라감
    private int id; // auto_increment

    @Column(nullable = false, length = 30)
    private String username;
    
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @ColumnDefault(" 'user' ")
    private String role; // 나중엔 Enum을 사용하는게 좋다.

    @CreationTimestamp //  시간이 자동으로 입력됨
    private Timestamp createDate;


}
