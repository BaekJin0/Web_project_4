package site.metacoding.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.love.Love;
import site.metacoding.domain.love.LoveRespository;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.post.RestaurantRepository;
import site.metacoding.domain.user.User;
import site.metacoding.web.dto.love.LoveReqDto;

@RequiredArgsConstructor
@Service
public class LoveService {

    private final LoveRespository loveRespository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void 담기(LoveReqDto loveReqDto, Integer postId, User principal) {
        Optional<Restaurant> restOp = restaurantRepository.findById(postId);

        if (restOp.isPresent()) {
            Restaurant postEntity = restOp.get();
            Love love = loveReqDto.toEntity(postEntity, principal);
            loveRespository.save(love);
        } else {
            throw new RuntimeException("이미 삭제된 글입니다.");
        }
    }

}
