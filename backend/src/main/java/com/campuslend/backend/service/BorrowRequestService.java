package com.campuslend.backend.service;

import com.campuslend.backend.dto.request.CreateBorrowRequestRecord;
import com.campuslend.backend.dto.response.BorrowRequestResponse;
import com.campuslend.backend.entity.BorrowRequest;
import com.campuslend.backend.entity.User;
import com.campuslend.backend.exception.ResourceNotFoundException;
import com.campuslend.backend.mapper.BorrowRequestMapper;
import com.campuslend.backend.repository.BorrowRequestRepository;
import com.campuslend.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowRequestService {
    private final BorrowRequestMapper borrowRequestMapper;
    private final BorrowRequestRepository borrowRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public BorrowRequestResponse createRequest(CreateBorrowRequestRecord dto , UUID currentUserId){
        User requester = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        BorrowRequest request = borrowRequestMapper.toEntity(dto);
        request.setRequester(requester);
        request.setCampus(requester.getCampus());
        BorrowRequest saved = borrowRequestRepository.saveAndFlush(request);
        return borrowRequestMapper.toResponse(saved);
    }
}
