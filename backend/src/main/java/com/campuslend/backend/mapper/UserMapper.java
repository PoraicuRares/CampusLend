package com.campuslend.backend.mapper;

import com.campuslend.backend.dto.request.RegisterRequest;
import com.campuslend.backend.dto.response.UserResponse;
import com.campuslend.backend.dto.response.UserSummary;
import com.campuslend.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserSummary toSummary(User user);
    UserResponse toResponse(User user);

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "password" , ignore = true)
    @Mapping(target = "karmaScore" , ignore = true)
    @Mapping(target = "totalLends" , ignore = true)
    @Mapping(target = "averageRating" , ignore = true)
    @Mapping(target = "password" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    User toEntity(RegisterRequest request);
}
