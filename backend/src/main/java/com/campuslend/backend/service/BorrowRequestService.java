package com.campuslend.backend.service;

import com.campuslend.backend.dto.request.CreateBorrowRequestRecord;
import com.campuslend.backend.dto.response.BorrowRequestResponse;
import com.campuslend.backend.entity.BorrowRequest;
import com.campuslend.backend.entity.User;
import com.campuslend.backend.entity.enums.Campus;
import com.campuslend.backend.exception.ResourceNotFoundException;
import com.campuslend.backend.mapper.BorrowRequestMapper;
import com.campuslend.backend.repository.BorrowRequestRepository;
import com.campuslend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowRequestService {
    private final BorrowRequestMapper borrowRequestMapper;
    private final BorrowRequestRepository borrowRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public BorrowRequestResponse createRequest(CreateBorrowRequestRecord dto, UUID currentUserId) {
        User requester = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        BorrowRequest request = borrowRequestMapper.toEntity(dto);
        request.setRequester(requester);
        request.setCampus(requester.getCampus());
        BorrowRequest saved = borrowRequestRepository.saveAndFlush(request);
        return borrowRequestMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<BorrowRequestResponse> getFeed(Campus campus, String search, Pageable pageable) {
        String normalizedSearch = (search == null) ? "" : search.trim();
        return borrowRequestRepository.findFeed(campus, normalizedSearch, pageable)
                .map(borrowRequestMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public BorrowRequestResponse getRequestById(UUID borrowRequestId) {
        return borrowRequestMapper.toResponse(
                borrowRequestRepository.findByIdWithRequester(borrowRequestId)
                        .orElseThrow(() -> new ResourceNotFoundException("Borrow request not found")));
    }

    @Transactional(readOnly = true)
    public List<BorrowRequestResponse> getMyRequests(UUID currentUserId){
        return borrowRequestMapper.toResponseList(
                borrowRequestRepository.findMyRequests(currentUserId));
    }
}
