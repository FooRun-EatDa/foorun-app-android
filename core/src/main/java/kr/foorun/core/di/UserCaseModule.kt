package kr.foorun.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserCaseModule {

//    @Provides
//    fun providesGetSomethingUseCase(repository: GithubRepository): GetGithubReposUseCase {
//        return GetGithubReposUseCase(repository)
//    }
}