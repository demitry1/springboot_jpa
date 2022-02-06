package com.edu.spoot.entity;
import lombok.*;
import javax.persistence.*;

@Entity   // jpa로 관리되는 엔티티 객체임을 나타냄
@Table(name="tbl_memo")  //테이블 생성정보
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Memo {
    @Id    //PK필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //키 자동생성
    private long mno;
    
    @Column(length = 200, nullable = false)  //테이블 추가필드 생성
    private String memoText;
}
