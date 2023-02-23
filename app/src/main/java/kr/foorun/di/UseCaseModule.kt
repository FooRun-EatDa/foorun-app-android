package kr.foorun.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

//    @Provides
//    fun providesGetSomethingUseCase(repository: GithubRepository): GetGithubReposUseCase {
//        return GetGithubReposUseCase(repository)
//    }
}