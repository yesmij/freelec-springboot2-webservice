package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor        // 롬복이 final 필드를 인자값으로 하는 생성자를 생성해줌
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();   // todo why??
    }

    @Transactional  //(readOnly = true)                     // 트랜잭션 범위는 유지학, 조회만 하는 경우, 속도가 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)         // .map( posts -> new PostsListResponseDto(posts) )
                .collect(Collectors.toList());
    }


    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id)  );
        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니당. id = " + id) );

        return new PostsResponseDto(posts);
    }
}
