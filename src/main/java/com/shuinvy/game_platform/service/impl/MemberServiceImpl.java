package com.shuinvy.game_platform.service.impl;

import com.shuinvy.game_platform.dao.MemberDao;
import com.shuinvy.game_platform.dao.MemberInfoDao;
import com.shuinvy.game_platform.dto.MemberInfoRequest;
import com.shuinvy.game_platform.dto.MemberRequest;
import com.shuinvy.game_platform.dto.MemberResponse;
import com.shuinvy.game_platform.model.Member;
import com.shuinvy.game_platform.model.MemberInfo;
import com.shuinvy.game_platform.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberInfoDao memberInfoDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MemberResponse getById(Integer id) {
        Member member = memberDao.getById(id);
        if (member == null) {
            return null;
        }
        MemberInfo memberInfo = memberInfoDao.getByMemberId(member.getId());
        return new MemberResponse(member, memberInfo);
    }

    @Override
    public Member getByUsername(String username) {
        return memberDao.getByUsername(username);
    }

    @Override
    public boolean checkExists(String username, Integer id) {
        Member member = getByUsername(username);
        if (member == null) {
            return false;
        }
        if (id == 0) {
            return true;
        }
        return !member.getId().equals(id);
    }

    @Override
    public List<MemberResponse> getList() {
        return memberDao.getListWithInfo();
    }

    @Override
    public Integer create(MemberRequest request, String ip) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Integer newId = memberDao.create(request);
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest();
        memberInfoRequest.setName(request.getName());
        Integer memberInfoId = memberInfoDao.create(newId, memberInfoRequest, ip);
        return memberInfoId == 0 ? 0 : newId;
    }

    @Override
    public void update(Integer id, MemberRequest request) {
        Member old = memberDao.getById(id);
        if (request.getUsername() == null) {
            request.setUsername(old.getUsername());
        }
        if (request.getPassword() == null) {
            request.setPassword(old.getPassword());
        } else {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getEmail() == null) {
            request.setEmail(old.getEmail());
        }
        if (request.getStatus() == null) {
            request.setStatus(old.getStatus());
        }
        memberDao.update(id, request);
        MemberInfo memberInfo = memberInfoDao.getByMemberId(id);
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest();
        if (request.getName() == null) {
            memberInfoRequest.setName(memberInfo.getName());
        } else {
            memberInfoRequest.setName(request.getName());
        }
        memberInfoRequest.setPoint(memberInfo.getPoint());
        memberInfoDao.update(memberInfo.getId(), memberInfoRequest);
    }

    @Override
    public void delete(Integer id) {
        MemberInfo memberInfo = memberInfoDao.getByMemberId(id);
        memberDao.delete(id);
        memberInfoDao.delete(memberInfo.getId());
    }
}
