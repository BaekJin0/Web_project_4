package site.metacoding.web.dto.love;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.domain.love.Love;
import site.metacoding.domain.post.Restaurant;
import site.metacoding.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoveReqDto {

    private Restaurant postId;
    private User userId;

    public Love toEntity(Restaurant restaurant, User principal) {
        Love love = new Love();

        love.setRestaurant(restaurant);
        love.setUser(principal);

        return love;
    }

}
