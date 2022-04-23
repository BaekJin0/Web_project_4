package site.metacoding.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.user.User;
import site.metacoding.domain.user.UserRepository;

@RequiredArgsConstructor
// 트랜잭션을 관리하는 오브젝트 : 서비스
// 기능들의 모임 (비즈니스 로직)
@Service // 컴포넌트 스캔시에 IoC 컨테이너에 등록
public class UserService {

    private final UserRepository userRepository;

    public String 유저아이디검사(String id) {
        User userEntity = userRepository.mUsernameSameCheck(id);

        if (userEntity == null) {
            return "없어";
        } else {
            return "있어";
        }
    }

    public String 유저이메일검사(String email) {
        User userEntity = userRepository.mEmailSameCheck(email);

        if (userEntity == null) {
            return "없어";
        } else {
            return "있어";
        }
    }

    @Transactional
    public void 회원가입(User user) {
        // save하면 DB에 INSERT하고 INSERT된 결과를 다시 return 해준다. -> jpa가 리턴해줌

        userRepository.save(user);
    }

    public Optional<User> 로그인(User user) {
        // 로그인 처리 쿼리를 JPA에서 제공해주지 않음 -> nativeQuery 생성
        return userRepository.mLogin(user.getId(), user.getPassword());
    }

}