package com.sbkinoko.sbkinokorpg.repository.bagrepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class BagRepositoryModule {

    @Singleton
    @Provides
    static BagRepository provideBagRepository() {
        return new BagRepositoryImpl();
    }
}
