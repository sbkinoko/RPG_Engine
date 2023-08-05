package com.sbkinoko.sbkinokorpg.repository;

import com.sbkinoko.sbkinokorpg.repository.bagrepository.BagRepository;

import javax.inject.Singleton;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

// todo 解釈を考える https://dagger.dev/hilt/entry-points
// 使用箇所で定義するか、ここでいいのか
@EntryPoint
@InstallIn(SingletonComponent.class)
public interface MyEntryPoints {
    @Singleton
    BagRepository bagRepository();
}