package jpabook1.jpashop1.service;

import jpabook1.jpashop1.domain.Member;
import jpabook1.jpashop1.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static jpabook1.jpashop1.domain.Member.member;
import static org.junit.jupiter.api.Assertions.*;
//자바 안에 띄울 때 자바 안에 데이터베이스 살짝 띄어서 만드는 법. 테스트 돌릴려고 뭘 하기엔 귀찮으니까 스프링은 공짜로 줌
@SpringBootTest //스프링 안에서 테스트 돌리는 거 무조건 필요
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;
    @Rollback(false)//눈으로 직관적으로 확인할 수 있음. 롤백용이여서 필요

    @Test
    public void 회원가입() throws Exception {
        //given
        member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);
        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }



    @Test
    public void 증복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2); //예외가 발생해야 한다. 이름이 같아서
        });
    }


}