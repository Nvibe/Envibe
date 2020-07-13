package com.envibe.envibe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FollowController {
    //@Autowired
    //RelationshipDao relatinshipDao;

    @PostMapping("/api/v1/follow")
    public void apiFollowUser(Model model, HttpServletRequest request, @RequestParam(required = true) String username) {
        //relationshipDao.create(request.getRemoteUser(), username);
    }
}
