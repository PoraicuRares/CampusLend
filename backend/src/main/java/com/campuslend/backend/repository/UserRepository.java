package com.campuslend.backend.repository;

import com.campuslend.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User , UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE User u
            SET u.karmaScore = u.karmaScore + :points,
                u.totalLends = u.totalLends + 1
            WHERE u.id  = :id
            """)
    void rewardUserForCompletedLend(@Param("id") UUID id , @Param("points") double points);


    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE User u
            SET u.averageRating= (u.averageRating * u.ratingCount + :rating) /(u.ratingCount + 1),
                u.ratingCount = u.ratingCount + 1
            WHERE u.id = :id
            """)
    void addRatingToAverage(@Param("id") UUID id , @Param("rating") double rating);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE User u
            SET u.karmaScore = u.karmaScore * :factor
            WHERE u.karmaScore > :threshold
            """)
    void multiplyKarmaAboveThreshold(@Param("factor") double factor,
                                     @Param("threshold") double threshold);


}
