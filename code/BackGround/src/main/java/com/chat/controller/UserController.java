package com.chat.controller;

import com.chat.entity.Tag;
import com.chat.entity.User;
import com.chat.service.tag.TagService;
import com.chat.service.user.UserService;
import com.chat.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;

    /**
     * 利用用户邮箱获取user的信息
     * @param email 用户邮箱
     * @return 用户信息
     */
    @RequestMapping(value = "/user/getUserMessage.do" ,method = RequestMethod.GET)
    @ResponseBody
    public User getUserMessage(String email){
        User user=userService.getUserMessage(email);
        if(user==null){
            return null;
        }

        return user;
    }

    /**
     * 修改用户信息
     * @param username 用户名
     * @param signature 用户签名
     * @return
     */
    @RequestMapping(value = "/user/setUserMessage.do",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> setUserMessage(String username,String signature,String email){
        if(userService.setUserMessage(username,signature,email)){
            return ResponseUtil.getSuccessResponse();
        }else {
            return ResponseUtil.getFailureResponse("传递参数不正确！");
        }
    }

    /**
     * 用户签到接口
     * @param email 用户邮箱
     * @return 返回是否签到成功
     */
    @RequestMapping(value = "/user/signIn.do",method = RequestMethod.GET)
    @ResponseBody
    public Map signIn(String email){
        if(userService.signIn(email)){
            return ResponseUtil.getSuccessResponse();
        }else {
            return ResponseUtil.getFailureResponse("签到失败！用户不存在或者用户已经签到！");
        }
    }

    /**
     * 获取用户Tag的接口
     * @param email 用户email
     * @return 返回用户的tag集合
     */
    @RequestMapping(value = "/user/getUserTags.do",method = RequestMethod.GET)
    @ResponseBody
    public Set<Tag> getUserTags(String email){
        Set<Tag> set=tagService.getUserTags(email);
        return set;
    }

    /**
     * 修改用户的标签集
     * @param email 用户邮箱
     * @param tagsName 标签名字表
     * @return 是否修改成功
     */
    @RequestMapping(value = "/user/setUserTags.do",method = RequestMethod.GET)
    @ResponseBody
    public Map setUserTags(String email,String[] tagsName){
        if(tagService.setUserTags(email,tagsName)){
            return ResponseUtil.getSuccessResponse("修改成功！");
        }else {
            return ResponseUtil.getFailureResponse("找不到用户！");
        }
    }
}
