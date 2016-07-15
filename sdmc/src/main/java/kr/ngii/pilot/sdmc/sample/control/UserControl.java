/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.ngii.pilot.sdmc.sample.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ngii.pilot.sdmc.sample.model.User;
import kr.ngii.pilot.sdmc.sample.service.UserService;

@Controller
public class UserControl {
    static final Logger LOGGER = LoggerFactory.getLogger(UserControl.class);


    @Autowired
    protected UserService userService;

    @RequestMapping(value = "/sample/userList.ngii")
    public String getUserList(Model model) throws Exception {
        model.addAttribute("users",userService.selectAllUsers());
        return "sample/UserList";
    }

    @RequestMapping(value = "/sample/getUserInfoJson.ngii")
    public @ResponseBody Object getUserInfoJson(User user) throws Exception {
        return userService.selectUser(user);
    }

    @RequestMapping(value = "/sample/getUserInfoExceptionJson.ngii")
    public @ResponseBody Object getUserInfoExceptionJson(User user) throws Exception {
        // if(true) throw new BizException("오류 테스트 입니다.");
        return userService.selectUser(user);
    }

    @RequestMapping(value = "/sample/saveUserInfoJson.ngii")
    public @ResponseBody Object saveUserInfoJson(User user) throws Exception {
        if (user.getId() != null && !user.getId().isEmpty()) {
            userService.updateUser(user);
        } else {
            user.setPassWord("test");
            userService.insertUser(user);
        }
        return user;
    }

}
