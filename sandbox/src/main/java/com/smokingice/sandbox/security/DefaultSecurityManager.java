package com.smokingice.sandbox.security;

import java.security.Permission;

/**
 * @author smokingice
 */
public class DefaultSecurityManager extends SecurityManager {

    //检查所有的权限
    @Override
    public void checkPermission(Permission permission){
        System.out.println("默认禁用权限");
//        super.checkPermission(permission);
//        throw new SecurityException("权限不足" + permission.getActions());
    }

}
