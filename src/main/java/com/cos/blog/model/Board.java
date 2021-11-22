package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) // 연관관계 Many : Board, One: User / fetch = FetchType.EAGER : 무조건 들고와라. LAZY: 필요할때 들고와라
    @JoinColumn(name="userId") // 실제로 DB에 만들어질 때의 이름
    private User user; // FK. DB는 오브젝트를 저장할 수 없지만 자바는 저장할 수 있음

    // Joincoumn은 FK설정을 해주는거라서 필요 없고 그냥 join 해서 넣어달라는 의미
    // mappedBy : 나는 연관관계의 주인이 아니다. 난 FK가 아니에요. DB에 컬럼을 만들지 마라.
    // 테이블에 생성되는 FK가 아니다.
    @OneToMany(mappedBy = "board",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // 하나의 게시글은 여러개의 답변을 가질 수 있음 / Reply table에 있는 board를 넣어줌
    @JsonIgnoreProperties({"board"}) // 무한참조 방지
    @OrderBy("id desc")
    private List<Reply> replies; // 게시글에 답변이 여러개이므로 List

    @CreationTimestamp
    private Timestamp createDate;

}
