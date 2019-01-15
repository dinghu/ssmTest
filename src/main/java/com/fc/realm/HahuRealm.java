package com.fc.realm;

import com.fc.mapper.UserMapper;
import com.fc.model.User;
import com.fc.service.UserService;
import com.fc.util.MyUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HahuRealm extends AuthorizingRealm {
    //    @Autowired
//    UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doClearCache(PrincipalCollection principals) {
        super.doClearCache(principals);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("HahuRealm: doGetAuthenticationInfo");
        //用户名
        String username = (String) authenticationToken.getPrincipal();
        //密码
        String password = new String((char[]) authenticationToken.getCredentials());
        String md5Pwd = MyUtil.md5(password);
        Integer userId = userMapper.selectUserIdByEmailAndPassword(username, md5Pwd);
        User user = userMapper.selectUserInfoByUserId(userId);
        if (userId == null || user == null) {
            //没有该用户名
            throw new UnknownAccountException();
        }

        //身份验证通过,返回一个身份信息
        AuthenticationInfo aInfo = new SimpleAuthenticationInfo(username, password, getName());
        return aInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("HahuRealm: doGetAuthorizationInfo");
        return null;
    }
}
