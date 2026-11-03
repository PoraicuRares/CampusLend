package com.campuslend.backend.repository;

import com.campuslend.backend.entity.Transaction;
import com.campuslend.backend.entity.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.lender
            JOIN FETCH t.borrowRequest br
            JOIN FETCH br.requester
            WHERE br.requester.id = :requesterId
              AND t.status = :status
            ORDER BY t.createdAt DESC
            """)
    List<Transaction> findInbox(@Param("requesterId") UUID requesterId,
                                @Param("status") TransactionStatus status);

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.lender
            JOIN FETCH t.borrowRequest br
            JOIN FETCH br.requester
            WHERE t.borrowRequest.id = :requestId
            ORDER BY t.createdAt DESC
            """)
    List<Transaction> findOffersForRequest(@Param("requestId") UUID requestId);

    @Query("""
            SELECT t FROM Transaction t
            JOIN FETCH t.lender
            JOIN FETCH t.borrowRequest br
            JOIN FETCH br.requester
            WHERE t.lender.id = :lenderId
            ORDER BY t.createdAt DESC
            """)
    List<Transaction> findMyLends(@Param("lenderId") UUID lenderId);

    boolean existsByBorrowRequestIdAndLenderIdAndStatus(UUID borrowRequestId,
                                                        UUID lenderId,
                                                        TransactionStatus status);

    @Modifying(clearAutomatically = true , flushAutomatically = true)
    @Query("""
            UPDATE Transaction t
            SET t.status = :newStatus
            WHERE t.borrowRequest.id = :requestId
              AND t.status = :currentStatus
              AND t.id <> :acceptedId
            """)
    void updateOtherOffersStatus(@Param("requestId") UUID requestId,
                                 @Param("acceptedId") UUID acceptedId,
                                 @Param("currentStatus") TransactionStatus currentStatus,
                                 @Param("newStatus") TransactionStatus newStatus);

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.status = :status
              AND t.returnDeadline < :now
            """)
    List<Transaction> findOverdue(@Param("status") TransactionStatus status,
                                  @Param("now") LocalDateTime now);
}
