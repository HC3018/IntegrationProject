package com.teamwork.integrationproject.controller;


import com.teamwork.integrationproject.entity.TUser;
import com.teamwork.integrationproject.service.ITUserService;
import com.teamwork.integrationproject.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haoChaoJie
 * @since 2020-08-05
 */
@RestController
@RequestMapping("user")
public class TUserController {
    @Autowired
    private ITUserService itUserService;

    @GetMapping("test")
    public String test(){
        System.out.println("哈哈哈哈");
        return "hahah";
    }
    /**
     * 单个新增接口
     */
    @PostMapping("addOne")
    public void addOne(@RequestBody Map<String, Object> params){
        System.out.println(params);
        TUser tUser = new TUser();
        tUser.setUId((Integer) params.get("uId"));
        tUser.setUAge((Integer) params.get("uAge"));
        tUser.setUSex((String) params.get("uSex"));
        tUser.setUUserName((String) params.get("uUsername"));
        itUserService.insertInto(tUser);
    }
    @PostMapping("add")
    public Result addOne(@RequestBody TUser tUser){
        System.out.println(tUser);
//        return itUserService.insertInto(tUser);
        return null;
    }
    @GetMapping("qryList")
    public Result qryList(){
        List<TUser> tUsers = itUserService.qryList();
//        return itUserService.insertInto(tUser);
        return new Result(tUsers);
    }
}
