package com.shuinvy.game_platform.dao;

import com.shuinvy.game_platform.dto.MemberInfoRequest;
import com.shuinvy.game_platform.model.MemberInfo;

import java.util.List;

public interface MemberInfoDao {

    MemberInfo getById(Integer id);

    MemberInfo getByMemberId(Integer memberId);

    List<MemberInfo> getList();

    Integer create(Integer memberId, MemberInfoRequest request, String ip);

    void update(Integer id, MemberInfoRequest request);

    void delete(Integer id);
}
