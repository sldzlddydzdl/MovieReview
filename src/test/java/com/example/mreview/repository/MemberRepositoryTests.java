package com.example.mreview.repository;

import com.example.mreview.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("1. members 에 데이터를 삽입")
    public void insertMembers(){

        IntStream.rangeClosed(1, 100).forEach(i ->{
            Member member = Member.builder()
                    .email("r" + i + "@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void deleteMemberTest(){

        Long mid = 2L; // Member 의 mid

        Member member = Member.builder().mid(mid).build();

        // 순서!
//        memberRepository.deleteById(mid); // FK 로 참조하고 있는 상태이기 때문에 PK 쪽을 먼저 삭제할 수 없습니다.
//        reviewRepository.deleteByMember(member); //

        // FK 를 먼저 삭제하고 그다음 PK 를 삭제해야한다.
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

    }



}
