package site.metacoding.domain.love;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoveRespository extends JpaRepository<Love, Integer> {

    @Query(value = "SELECT * FROM love WHERE userNo = :userNo ORDER BY id DESC", nativeQuery = true)
    List<Love> findByUserId(@Param("userNo") Integer userNo);
}
