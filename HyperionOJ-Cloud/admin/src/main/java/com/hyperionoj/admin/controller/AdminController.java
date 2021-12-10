package com.hyperionoj.admin.controller;

import com.hyperionoj.common.feign.AdminClients;
import com.hyperionoj.common.vo.RegisterParam;
import com.hyperionoj.common.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/4
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    @Resource
    private AdminClients adminClients;

    @PostMapping("/add")
    public Result addAdmin(@RequestBody RegisterParam registerParam) {
        return adminClients.addAdmin(registerParam);
    }

    @PostMapping("/update")
    public Result updateAdmin(@RequestBody RegisterParam registerParam) {
        return adminClients.updateAdmin(registerParam);
    }

    @PostMapping("/delete")
    public Result deleteAdmin(@RequestBody String id){
        return adminClients.deleteAdmin(id);
    }

    @PostMapping("/freeze")
    public Result freezeUser(@RequestBody String id){
        return adminClients.freezeUser(id);
    }

}
