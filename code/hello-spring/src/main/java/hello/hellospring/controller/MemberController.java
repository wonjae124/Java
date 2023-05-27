package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // 필드 주입 방법
    // @Autowired private MemberService memberService;
    private  MemberService memberService;
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @Autowired // 스프링 컨테이너에 있는 멤버 서비스를 멤버 컨트롤러에 연결한다. 생성자 의존성 주입.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberSerice = " + memberService.getClass());
    }

    @GetMapping("/members/new") // 조회할 떄(url입력) 주로 쓰는 getMapping
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 데이터를 폼에 넣어서 전달할 때 주로 쓰는 postmapping
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/"; // 홈 화면 끝나니깐 리다이렉트한다.(다시 보낸다)
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
