package com.campuslend.backend.repository;

import com.campuslend.backend.entity.BorrowRequest;
import com.campuslend.backend.entity.enums.Campus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowRequestRepository extends JpaRepository<BorrowRequest , UUID> {
    @Query(
            value = """
                    SELECT br FROM BorrowRequest br
                    JOIN FETCH br.requester u
                    WHERE br.campus = :campus
                        AND br.active = true
                        AND LOWER(br.title) LIKE LOWER(CONCAT('%' , :search , '%'))
                    ORDER BY u.karmaScore DESC , br.createdAt DESC
                    """,
            countQuery = """
                    SELECT COUNT(br) FROM BorrowRequest br
                    WHERE br.campus = :campus
                        AND br.active = true
                        AND LOWER(br.title) LIKE LOWER(CONCAT('%' , :search , '%'))
                    """
    )
    Page<BorrowRequest> findFeed(@Param("campus") Campus campus,
                                 @Param("search") String search,
                                 Pageable pageable);

    @Query("""
            SELECT br FROM BorrowRequest br
            WHERE br.requester.id = :requesterid
            ORDER BY br.createdAt DESC
            """)
    List<BorrowRequest> findMyRequests(@Param("requesterId") UUID requesterId);
}
