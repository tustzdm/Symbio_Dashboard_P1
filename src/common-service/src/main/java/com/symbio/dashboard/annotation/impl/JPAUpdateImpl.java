package com.symbio.dashboard.annotation.impl;

import com.symbio.dashboard.annotation.JPAUpdate;

import java.lang.annotation.Annotation;

public class JPAUpdateImpl implements JPAUpdate {
    @Override
    public boolean includeNull() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
