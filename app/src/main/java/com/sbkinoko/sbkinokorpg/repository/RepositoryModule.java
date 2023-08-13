package com.sbkinoko.sbkinokorpg.repository;

import com.sbkinoko.sbkinokorpg.repository.bagrepository.BagRepository;
import com.sbkinoko.sbkinokorpg.repository.bagrepository.BagRepositoryImpl;
import com.sbkinoko.sbkinokorpg.repository.playertool.PlayerToolRepository;
import com.sbkinoko.sbkinokorpg.repository.playertool.PlayerToolRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class RepositoryModule {

    @Singleton
    @Provides
    static BagRepository provideBagRepository() {
        return new BagRepositoryImpl();
    }

    @Singleton
    @Provides
    static PlayerToolRepository providePlayerToolRepository() {
        return new PlayerToolRepositoryImpl();
    }
}
