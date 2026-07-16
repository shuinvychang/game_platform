package com.shuinvy.game_platform.service;

import com.shuinvy.game_platform.dto.MemberRequest;
import com.shuinvy.game_platform.dto.MemberResponse;
import com.shuinvy.game_platform.model.Member;

import java.util.List;

public interface MemberService {

    MemberResponse getById(Integer id);

    Member getByUsername(String username);

    boolean checkExists(String username, Integer id);

    List<MemberResponse> getList();

    Integer create(MemberRequest request, String ip);

    void update(Integer id, MemberRequest request);

    void delete(Integer id);
}
