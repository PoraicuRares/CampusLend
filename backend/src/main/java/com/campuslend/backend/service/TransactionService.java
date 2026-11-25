package com.campuslend.backend.service;

import com.campuslend.backend.dto.request.CreateOfferRequest;
import com.campuslend.backend.dto.response.TransactionResponse;
import com.campuslend.backend.entity.BorrowRequest;
import com.campuslend.backend.entity.Transaction;
import com.campuslend.backend.entity.User;
import com.campuslend.backend.entity.enums.TransactionStatus;
import com.campuslend.backend.exception.BadRequestException;
import com.campuslend.backend.exception.ConflictException;
import com.campuslend.backend.exception.ForbiddenException;
import com.campuslend.backend.exception.ResourceNotFoundException;
import com.campuslend.backend.mapper.TransactionMapper;
import com.campuslend.backend.repository.BorrowRequestRepository;
import com.campuslend.backend.repository.TransactionRepository;
import com.campuslend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BorrowRequestRepository borrowRequestRepository;

    @Transactional
    public TransactionResponse createOffer(CreateOfferRequest createOfferRequest, UUID currentUserId, UUID borrowRequestId) {
        BorrowRequest request = borrowRequestRepository.findByIdWithRequester(borrowRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow request not found"));

        if (!request.isActive()) {
            throw new ConflictException("The request is not active anymore");
        }

        if (request.getRequester().getId().equals(currentUserId)) {
            throw new BadRequestException("You cannot offer to lend on your own request");
        }

        if (transactionRepository.existsByBorrowRequestIdAndLenderIdAndStatus(borrowRequestId, currentUserId, TransactionStatus.PENDING)) {
            throw new ConflictException("You already offered on this request");
        }

        User lender = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Transaction transaction = transactionMapper.toEntity(createOfferRequest);
        transaction.setBorrowRequest(request);
        transaction.setLender(lender);
        Transaction saved = transactionRepository.saveAndFlush(transaction);
        return transactionMapper.toResponse(saved, null);
    }

    @Transactional
    public TransactionResponse cancelOffer(UUID currentUserId , UUID transactionId){
        Transaction transaction = transactionRepository.findByIdWithDetails(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if(!transaction.getLender().getId().equals(currentUserId)){
            throw new ForbiddenException("You can only cancel your own offers");
        }

        if(transaction.getStatus() != TransactionStatus.PENDING){
            throw new ConflictException("Transaction must be pending");
        }

        transaction.setStatus(TransactionStatus.CANCELLED);

        return transactionMapper.toResponse(transaction , null);
    }

    @Transactional
    public TransactionResponse rejectOffer(UUID currentUserId , UUID transactionId){
        Transaction transaction = transactionRepository.findByIdWithDetails(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if(!transaction.getBorrowRequest().getRequester().getId().equals(currentUserId)){
            throw new ForbiddenException("You can only reject offers on your own requests");
        }

        if(transaction.getStatus() != TransactionStatus.PENDING){
            throw new ConflictException("Transaction must be pending");
        }

        transaction.setStatus(TransactionStatus.REJECTED);
        return transactionMapper.toResponse(transaction , null);
    }

    @Transactional
    public TransactionResponse acceptOffer(UUID currentUserId , UUID transactionId){
        Transaction transaction = transactionRepository.findByIdWithDetails(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if(!transaction.getBorrowRequest().getRequester().getId().equals(currentUserId)){
            throw new ForbiddenException("You can only accept offers on your own requests");
        }

        if(transaction.getStatus() != TransactionStatus.PENDING){
            throw new ConflictException("Transaction must be pending");
        }

        if(!transaction.getBorrowRequest().isActive()){
            throw new ConflictException("The request is not available anymore");
        }

        transaction.setStatus(TransactionStatus.ACCEPTED);
        transaction.setAcceptedAt(LocalDateTime.now());
        transaction.setReturnDeadline(LocalDateTime.now().plusHours(transaction.getLoanDuration().getDuration().toHours()));
        transaction.getBorrowRequest().setActive(false);

        transactionRepository.updateOtherOffersStatus(
                transaction.getBorrowRequest().getId(),
                transactionId,TransactionStatus.PENDING,
                TransactionStatus.REJECTED);

        return transactionMapper.toResponse(transaction,transaction.getLender().getPhoneNumber());
    }

}
