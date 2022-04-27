package site.metacoding.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.love.Love;
import site.metacoding.domain.user.User;
import site.metacoding.service.LoveService;
import site.metacoding.web.dto.love.LoveReqDto;

@RequiredArgsConstructor
@Controller
public class LoveController {
    private final LoveService loveService;
    private final HttpSession session;

    @PostMapping("/s/post/{postId}/love")
    public ResponseEntity<?> love(@PathVariable Integer postId, LoveReqDto loveReqDto) {

        User principal = (User) session.getAttribute("principal");

        loveService.담기(loveReqDto, postId, principal);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    // @GetMapping("/s/user/{id}/love")
    // public String myLoveList(@PathVariable Integer id, Model model) {
    // List<Love> loves = loveService.담기목록보기(id);
    // model.addAttribute("loves", loves);

    // return "/user/userInfo";
    // }
}
