package com.campuslend.backend.mapper;

import com.campuslend.backend.dto.request.CreateBorrowRequestRecord;
import com.campuslend.backend.dto.response.BorrowRequestResponse;
import com.campuslend.backend.dto.response.BorrowRequestSummary;
import com.campuslend.backend.entity.BorrowRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = UserMapper.class ,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BorrowRequestMapper {
    BorrowRequestResponse toResponse(BorrowRequest borrowRequest);
    List<BorrowRequestResponse> toResponseList(List<BorrowRequest> requests);
    BorrowRequestSummary toSummary(BorrowRequest borrowRequest);

    @Mapping(target ="id" , ignore = true)
    @Mapping(target ="campus" , ignore = true)
    @Mapping(target ="active" , ignore = true)
    @Mapping(target ="createdAt" , ignore = true)
    @Mapping(target ="requester" , ignore = true)
    @Mapping(target ="version" , ignore = true)
    BorrowRequest toEntity(CreateBorrowRequestRecord request);
}
