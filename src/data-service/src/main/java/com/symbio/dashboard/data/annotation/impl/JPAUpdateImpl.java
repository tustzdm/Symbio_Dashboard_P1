package com.symbio.dashboard.data.annotation.impl;

import com.symbio.dashboard.data.annotation.JPAUpdate;

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
