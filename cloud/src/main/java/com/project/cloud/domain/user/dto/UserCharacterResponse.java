package com.project.cloud.domain.user.dto;

public record UserCharacterResponse(
    int level,
    int exp,
    String image,
    BodyPartExpResponse bodyPartExp
) {

}
