package com.example.taskmanagerapp.data.remote.datasource

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    fun logoutUser() {
        firebaseAuth.signOut()
    }

    fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }
}