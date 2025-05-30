package com.dhontiveros.roverrobot.di

import com.dhontiveros.roverrobot.processor.RobotProcessor
import com.dhontiveros.roverrobot.processor.RobotProcessorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RobotProcessorModule {
    @Binds
    internal abstract fun provideRobotProcessor(impl: RobotProcessorImpl): RobotProcessor
}