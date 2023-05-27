package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
<<<<<<< HEAD

import org.junit.Assert;
=======
>>>>>>> c068d0d4f88b34d904702c0f820d5abd175d27d8
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit과 spring과 같이 실행하겠다
=======
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
>>>>>>> c068d0d4f88b34d904702c0f820d5abd175d27d8
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
<<<<<<< HEAD
    @Autowired
    EntityManager em;
=======
>>>>>>> c068d0d4f88b34d904702c0f820d5abd175d27d8
    
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }
<<<<<<< HEAD
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

//        try{
//            memberService.join(member2); // 예외 발생!
//        } catch(IllegalStateException e) {
//            return; }

        //then
        fail("예외가 발생해야 한다.");
=======
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        
        //when
        
        //then
>>>>>>> c068d0d4f88b34d904702c0f820d5abd175d27d8
    }

}