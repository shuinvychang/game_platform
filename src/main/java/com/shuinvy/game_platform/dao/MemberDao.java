package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.MemberRequest;
import com.shuinvy.game_platform.dto.MemberResponse;
import com.shuinvy.game_platform.model.Member;

import java.util.List;

public interface MemberDao {

    Member getById (Integer id);

    Member getByUsername(String username);

    List<Member> getList();

    List<MemberResponse> getListWithInfo();

    Integer create(MemberRequest request);

    void update(Integer id, MemberRequest request);

    void delete(Integer id);
}
