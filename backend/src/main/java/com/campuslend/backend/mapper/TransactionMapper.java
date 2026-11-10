package com.campuslend.backend.mapper;

import com.campuslend.backend.dto.request.CreateOfferRequest;
import com.campuslend.backend.dto.response.TransactionResponse;
import com.campuslend.backend.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class , BorrowRequestMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TransactionMapper {

    @Mapping(target = "contactPhone" , source = "contactPhone")
    TransactionResponse toResponse(Transaction transaction , String contactPhone);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "borrowRequest" , ignore = true)
    @Mapping(target = "lender" , ignore = true)
    @Mapping(target = "status" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "acceptedAt" , ignore = true)
    @Mapping(target = "returnDeadline" , ignore = true)
    @Mapping(target = "completedAt" , ignore = true)
    @Mapping(target = "rating" , ignore = true)
    @Mapping(target = "version" , ignore = true)
    Transaction toEntity(CreateOfferRequest request);
}
