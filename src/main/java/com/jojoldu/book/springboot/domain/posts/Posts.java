package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length=500, nullable = false)
    private String title;

    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder                                                    // 굳이 왜 이렇게 builder를 일일이 기입하는지?
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {          // Transaction안에서 DB에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지
        this.title = title;                                     // 이 상태에서 해당 데이터의 값을 변경하면, 트랜잭션이 끝나는 시점에 DB에 변경분 반영
        this.content = content;                                 // Entity 인스턴스 값만 변경하면 별도로 update 쿼리가 필요없음 -> Dirty Checking
    }
}
