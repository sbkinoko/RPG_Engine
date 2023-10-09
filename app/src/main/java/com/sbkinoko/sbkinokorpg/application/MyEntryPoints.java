package com.sbkinoko.sbkinokorpg.application;

import com.sbkinoko.sbkinokorpg.repository.bagrepository.BagRepository;
import com.sbkinoko.sbkinokorpg.repository.playertool.PlayerToolRepository;

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

    @Singleton
    PlayerToolRepository playerToolRepository();
}