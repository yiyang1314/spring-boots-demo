package com.boot.security.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        //MD5Util.encode((String) charSequence);
        System.out.println(charSequence.toString());
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        System.out.println(charSequence);
        System.out.println(s);
        return  s.equals(charSequence.toString());
    }
}
