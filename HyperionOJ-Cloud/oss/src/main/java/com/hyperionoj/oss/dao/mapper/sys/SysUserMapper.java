package com.hyperionoj.oss.dao.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyperionoj.oss.dao.pojo.sys.SysUser;
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
}
