package site.metacoding.web;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.handler.CustomException;
import site.metacoding.domain.user.User;
import site.metacoding.service.UserService;
import site.metacoding.util.UtilValid;
import site.metacoding.web.dto.ResponseDto;
import site.metacoding.web.dto.user.IdFindReqDto;
import site.metacoding.web.dto.user.JoinReqDto;
import site.metacoding.web.dto.user.LoginReqDto;
import site.metacoding.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/api/user/id/same-check")
    public @ResponseBody ResponseDto<String> sameIdCheck(String id) {
        String data = userService.유저아이디검사(id);
        return new ResponseDto<String>(1, "통신성공", data);
    }

    @GetMapping("/api/user/email/same-check")
    public @ResponseBody ResponseDto<String> sameEmailCheck(String email) {
        String data = userService.유저이메일검사(email);
        return new ResponseDto<String>(1, "통신성공", data);
    }

    // 웹브라우저 -> 회원가입 페이지 주세요!! (O)
    // 앱 -> 회원가입 페이지 주세요? (X)
    // 회원가입폼 (인증 X)
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        userService.회원가입(joinReqDto.toEntity());

        // redirect:매핑주소
        return "redirect:/login-form"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
    }

    // 로그인폼 (인증 X)
    @GetMapping("/login-form")
    public String loginForm(HttpServletRequest request, Model model) {
        // jSessionId=fjsdklfjsadkfjsdlkj333333;remember=ssar
        // request.getHeader("Cookie");
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies(); // jSessionId, remember 두개가 있음.

            for (Cookie cookie : cookies) {
                System.out.println("쿠키값 : " + cookie.getName());
                if (cookie.getName().equals("remember")) {
                    model.addAttribute("remember", cookie.getValue());
                }

            }
        }

        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid LoginReqDto loginReqDto, BindingResult bindingResult, HttpServletResponse response) {
        System.out.println("사용자로 부터 받은 username, password : " + loginReqDto);

        UtilValid.요청에러처리(bindingResult);

        Optional<User> userOp = userService.로그인(loginReqDto.toEntity());

        if (userOp.isPresent()) {
            User userEntity = userOp.get();

            userEntity.setRemember(loginReqDto.getRemember());

            session.setAttribute("principal", userEntity);

            if (userEntity.getRemember() != null && userEntity.getRemember().equals("on")) {
                response.addHeader("Set-Cookie", "remember=" + userEntity.getId());
            }

            return "redirect:/";

        } else {
            throw new CustomException("입력하신 아이디나 비밀번호가 틀렸습니다.");
        }

    }

    // 로그아웃 - 로그인O
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login-form"; // PostController 만들고 수정하자.
    }

}