package com.sbkinoko.sbkinokorpg.repository;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface MyEntryPoints {

    BagRepository bagRepository();
}
