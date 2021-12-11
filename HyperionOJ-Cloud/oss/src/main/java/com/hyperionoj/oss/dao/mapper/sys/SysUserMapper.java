package com.hyperionoj.oss.dao.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.oss.dao.pojo.sys.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hyperion
 * @date 2021/11/30
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 冻结普通用户
     *
     * @param id 要冻结的用户id
     */
    void freezeUser(Long id);

    /**
     * 用户提交了一次正确代码
     * @param authorId 用户id
     */
    void updateSubmitAc(@Param("authorId") Long authorId);

    /**
     * 用户提交了一次错误代码
     * @param authorId 用户id
     */
    void updateSubmitNoAc(@Param("authorId")Long authorId);
}
