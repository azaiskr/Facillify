package com.lidm.facillify.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lidm.facillify.data.local.dao.AppDatabase
import com.lidm.facillify.data.remote.api.ChatbotApiService
import com.lidm.facillify.data.repository.SiswaRepository
import com.lidm.facillify.data.repository.UserRepository
import com.lidm.facillify.data.repository.ThreadRepository
import com.lidm.facillify.di.Inject
import com.lidm.facillify.ui.viewmodel.AuthViewModel
import com.lidm.facillify.ui.viewmodel.ChatViewModel
import com.lidm.facillify.ui.viewmodel.DetailTrackingAnakViewModel
import com.lidm.facillify.ui.viewmodel.GayaBelajarViewModel
import com.lidm.facillify.ui.viewmodel.HomeSiswaViewModel
import com.lidm.facillify.ui.viewmodel.LatihanSiswaViewModel
import com.lidm.facillify.ui.viewmodel.LatihanSoalGuruViewModel
import com.lidm.facillify.ui.viewmodel.MateriBelajarViewModel
import com.lidm.facillify.ui.viewmodel.ProfileViewModel
import com.lidm.facillify.ui.viewmodel.RegisterViewModel
import com.lidm.facillify.ui.viewmodel.SiswaRiwayatViewModel
import com.lidm.facillify.ui.viewmodel.TambahLatianSoalGuruViewModel
import com.lidm.facillify.ui.viewmodel.TambahMateriBelajarViewModel
import com.lidm.facillify.ui.viewmodel.ThreadViewModel
import com.lidm.facillify.ui.viewmodel.TrackingAnakViewModel
import com.lidm.facillify.ui.viewmodel.UpdateParentEmailViewModel

class ViewModelFactory(
    private val repositories: Map<Class<*>, Any>
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                AuthViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                val chatbotApiService = repositories[ChatbotApiService::class.java] as ChatbotApiService
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                val database = repositories[AppDatabase::class.java] as AppDatabase
                ChatViewModel(chatbotApiService, siswaRepository, database) as T
            }
            modelClass.isAssignableFrom(ThreadViewModel::class.java) -> {
                val threadRepository = repositories[ThreadRepository::class.java] as ThreadRepository
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                ThreadViewModel(threadRepository, userRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                ProfileViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LatihanSiswaViewModel::class.java) -> {
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                LatihanSiswaViewModel(siswaRepository) as T
            }
            modelClass.isAssignableFrom(UpdateParentEmailViewModel::class.java) -> {
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                UpdateParentEmailViewModel(siswaRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SiswaRiwayatViewModel::class.java) -> {
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                SiswaRiwayatViewModel(siswaRepository) as T
            }
            modelClass.isAssignableFrom(TrackingAnakViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                TrackingAnakViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailTrackingAnakViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                DetailTrackingAnakViewModel(userRepository, siswaRepository) as T
            }
            modelClass.isAssignableFrom(TambahLatianSoalGuruViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                TambahLatianSoalGuruViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeSiswaViewModel::class.java) -> {
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                HomeSiswaViewModel(siswaRepository) as T
            }
            modelClass.isAssignableFrom(GayaBelajarViewModel::class.java) -> {
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                GayaBelajarViewModel(siswaRepository) as T
            }
            modelClass.isAssignableFrom(LatihanSoalGuruViewModel::class.java) -> {
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                LatihanSoalGuruViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MateriBelajarViewModel::class.java) ->{
                val siswaRepository = repositories[SiswaRepository::class.java] as SiswaRepository
                MateriBelajarViewModel(siswaRepository) as T
            }
            modelClass.isAssignableFrom(TambahMateriBelajarViewModel::class.java) ->{
                val userRepository = repositories[UserRepository::class.java] as UserRepository
                TambahMateriBelajarViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    mapOf(
                        UserRepository::class.java to Inject.provideAuthRepo(context.applicationContext),
                        ChatbotApiService::class.java to Inject.privodeChatAPiService(context.applicationContext),
                        ThreadRepository::class.java to Inject.provideThreadRepo(context.applicationContext),
                        SiswaRepository::class.java to Inject.provideSiswaRepo(context.applicationContext),
                        AppDatabase::class.java to Inject.provideDatabase(context.applicationContext)
                        // Tambahkan repository lainnya di sini
                    )
                ).also { instance = it }
            }
    }
}