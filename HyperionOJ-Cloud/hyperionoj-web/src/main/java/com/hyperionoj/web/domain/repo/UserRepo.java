package com.hyperionoj.web.domain.repo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyperionoj.web.infrastructure.mapper.UserMapper;
import com.hyperionoj.web.infrastructure.po.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo extends ServiceImpl<UserMapper, UserPO> {

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    public void freezeUser(Long id) {
        baseMapper.freezeUser(id);
    }

    /**
     * 用户提交了一次正确代码
     *
     * @param authorId 用户id
     */
    public void updateSubmitAc(Long authorId) {
        baseMapper.updateSubmitAc(authorId);
    }

    /**
     * 用户提交了一次错误代码
     *
     * @param authorId 用户id
     */
    public void updateSubmitNoAc(Long authorId) {
        baseMapper.updateSubmitNoAc(authorId);
    }
}
