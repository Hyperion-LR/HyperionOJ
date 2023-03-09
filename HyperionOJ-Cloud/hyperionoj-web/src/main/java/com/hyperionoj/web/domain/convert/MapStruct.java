package com.hyperionoj.web.domain.convert;

import com.hyperionoj.web.infrastructure.po.*;
import com.hyperionoj.web.presentation.dto.*;
import com.hyperionoj.web.presentation.dto.param.RegisterAdminParam;
import com.hyperionoj.web.presentation.vo.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MapStruct {

    public static CategoryVO toVO(CategoryDTO categoryDTO) {
        if(categoryDTO == null){
            return null;
        }
        return CategoryVO.builder()
                .categoryName(categoryDTO.getCategoryName())
                .description(categoryDTO.getDescription())
                .id(categoryDTO.getId()).build();
    }

    public static CategoryVO toVO(CategoryPO category) {
        if(category == null){
            return null;
        }
        return CategoryVO.builder()
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .id(category.getId().toString()).build();
    }

    public static CommentVO toVO(CommentDTO commentDTO) {
        if(commentDTO == null){
            return null;
        }
        return CommentVO.builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .parentId(commentDTO.getParentId())
                .problemId(commentDTO.getProblemId())
                .author(toVO(commentDTO.getAuthor()))
                .toUser(toVO(commentDTO.getToUser()))
                .supportNumber(commentDTO.getSupportNumber())
                .createDate(commentDTO.getCreateDate())
                .level(commentDTO.getLevel())
                .build();
    }

    public static ProblemVO toVO(ProblemDTO problemDTO) {
        if(problemDTO == null){
            return null;
        }
        return ProblemVO.builder()
                .id(problemDTO.getId())
                .title(problemDTO.getTitle())
                .acNumber(problemDTO.getAcNumber())
                .caseNumber(problemDTO.getCaseNumber())
                .category(toVO(problemDTO.getCategory()))
                .commentNumber(problemDTO.getCommentNumber())
                .problemBody(problemDTO.getProblemBody())
                .problemBodyHtml(problemDTO.getProblemBodyHtml())
                .problemLevel(problemDTO.getProblemLevel())
                .runMemory(problemDTO.getRunMemory())
                .runTime(problemDTO.getRunTime())
                .submitNumber(problemDTO.getSubmitNumber())
                .tags(toVOs(problemDTO.getTags()))
                .build();
    }

    private static List<TagVO> toVOs(List<TagDTO> tags) {
        return tags.stream().map(MapStruct::toVO).collect(Collectors.toList());
    }

    private static TagVO toVO(TagDTO tagDTO) {
        if(tagDTO == null){
            return null;
        }
        return TagVO.builder()
                .id(tagDTO.getId())
                .tagName(tagDTO.getTagName())
                .build();
    }

    public static ProblemVO toVO(ProblemPO problemPO) {
        if(problemPO == null){
            return null;
        }
        return ProblemVO.builder()
                .id(problemPO.getId().toString())
                .title(problemPO.getTitle())
                .acNumber(problemPO.getAcNumber())
                .caseNumber(problemPO.getCaseNumber())
                //.category(problemPO.getCategoryId())
                .commentNumber(problemPO.getCommentNumber())
                //.problemBodyVo(problemPO.getProblemBodyVo())
                .problemLevel(problemPO.getProblemLevel())
                .runMemory(problemPO.getRunMemory())
                .runTime(problemPO.getRunTime())
                .submitNumber(problemPO.getSubmitNumber())
                //.tags(problemPO.getTags())
                .build();
    }

    public static TagVO toVO(TagPO tagPO) {
        if(tagPO == null){
            return null;
        }
        return TagVO.builder()
                .id(tagPO.getId().toString())
                .tagName(tagPO.getTagName()).build();
    }

    public static UserVO toVO(UserDTO userDO) {
        if(userDO == null){
            return null;
        }
        return UserVO.builder()
                .id(userDO.getId())
                .username(userDO.getUsername())
                .avatar(userDO.getAvatar())
                .mail(userDO.getMail())
                .build();
    }

    public static UserVO toVO(UserPO userPO) {
        if(userPO == null){
            return null;
        }
        return UserVO.builder()
                .id(userPO.getId().toString())
                .username(userPO.getUsername())
                .avatar(userPO.getAvatar())
                .mail(userPO.getMail())
                .build();
    }

    public static AdminActionVO toVO(AdminActionPO adminActionPO) {
        if(adminActionPO == null){
            return null;
        }
        return AdminActionVO.builder()
                .id(adminActionPO.getId().toString())
                .action(adminActionPO.getAdminAction())
                .adminId(adminActionPO.getAdminAction())
                .time(adminActionPO.getActionTime().toString())
                .status(adminActionPO.getActionStatus())
                .build();
    }

    public static AdminVO toVO(AdminPO admin) {
        if(admin == null){
            return null;
        }
        return AdminVO.builder()
                .id(admin.getId().toString())
                .name(admin.getName())
                .password(admin.getPassword())
                .permissionLevel(admin.getPermissionLevel())
                .salt(admin.getSalt())
                .build();
    }

    public static AdminPO toPO(RegisterAdminParam registerParam) {
        return AdminPO.builder()
                .id(Long.parseLong(registerParam.getId()))
                .name(registerParam.getName())
                .password(registerParam.getPassword())
                .permissionLevel(registerParam.getPermissionLevel())
                .build();
    }

    public static ProblemPO toPO(ProblemDTO problemDTO) {
        ProblemPO.ProblemPOBuilder builder = ProblemPO.builder();
        if(!StringUtils.isEmpty(problemDTO.getId())){
            builder.id(Long.parseLong(problemDTO.getId()));
        }
        return builder
                .title(problemDTO.getTitle())
                .problemBody(problemDTO.getProblemBody())
                .problemBodyHtml(problemDTO.getProblemBodyHtml())
                .problemLevel(problemDTO.getProblemLevel())
                .caseNumber(problemDTO.getCaseNumber())
                .categoryId(Long.parseLong(problemDTO.getCategory().getId()))
                .acNumber(problemDTO.getAcNumber())
                .submitNumber(problemDTO.getSubmitNumber())
                .commentNumber(problemDTO.getCommentNumber())
                .runTime(problemDTO.getRunTime())
                .runMemory(problemDTO.getRunMemory())
                .build();
    }

    public static ProblemCommentPO toPO(CommentDTO commentDTO) {
        ProblemCommentPO.ProblemCommentPOBuilder builder = ProblemCommentPO.builder();
        if(!StringUtils.isEmpty(commentDTO.getId())){
            builder.id(Long.parseLong(commentDTO.getId()));
        }
        return builder.content(commentDTO.getContent())
                .authorId(Long.parseLong(commentDTO.getAuthor().getId()))
                .level(commentDTO.getLevel())
                .toUid(Long.parseLong(commentDTO.getToUser().getId()))
                .createTime(Long.parseLong(commentDTO.getCreateDate()))
                .problemId(Long.parseLong(commentDTO.getProblemId()))
                .parentId(Long.parseLong(commentDTO.getParentId()))
                .supportNumber(commentDTO.getSupportNumber())
                .build();
    }

    public static SubmitVO toVO(ProblemSubmitPO submit) {
        if(submit == null){
            return null;
        }
        return SubmitVO.builder()
                .id(submit.getId().toString())
                .problemId(submit.getProblemId().toString())
                .authorId(submit.getAuthorId().toString())
                .codeLang(submit.getCodeLang())
                .codeBody(submit.getCodeBody())
                .runTime(submit.getRunTime())
                .runMemory(submit.getRunMemory())
                .createTime(submit.getCreateTime().toString())
                .verdict(submit.getStatus())
                .build();
    }
}
