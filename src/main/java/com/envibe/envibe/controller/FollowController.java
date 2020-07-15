package com.envibe.envibe.controller;

import com.envibe.envibe.dao.FriendDao;
import com.envibe.envibe.model.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FollowController {
    @Autowired
    FriendDao relationshipDao;

    @GetMapping("/api/v1/follow")
    public void apiFollowUser(Model model, HttpServletRequest request, @RequestParam(required = true) String username) {
        relationshipDao.create(new Relationship(request.getRemoteUser(), username));
    }
}
