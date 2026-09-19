package com.example.taskmanagerapp.data.remote.datasource

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.taskmanagerapp.data.remote.model.TaskDocument
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

class TaskRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) {
    private val tasksCollection = firestore.collection("tasks")

    @Suppress("UNCHECKED_CAST")
    fun observeTasks(ownerId: String): Flow<List<TaskDocument>> = callbackFlow {
        val query = tasksCollection.whereEqualTo("ownerId", ownerId)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val docs = ArrayList<TaskDocument>()

            if (snapshot != null) {
                val clazz =
                    Class.forName(
                        "com.example.taskmanagerapp.data.remote.model.TaskDocument"
                    ) as Class<TaskDocument>

                for (doc in snapshot.documents) {
                    val taskDoc = doc.toObject(clazz)

                    if (taskDoc != null) {
                        docs.add(taskDoc.copy(id = doc.id))
                    }
                }
            }

            trySend(docs)
        }

        awaitClose {
            listener.remove()
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val network = connectivityManager.activeNetwork
            ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        ) &&
                capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )
    }

    suspend fun createTask(document: TaskDocument): String {

        // IMPORTANTE:
        // Si no hay Internet, no intentamos escribir en Firestore.
        // Así Firestore no deja la publicación pendiente.
        if (!hasInternetConnection()) {
            throw IOException(
                "No hay conexión a Internet. El borrador se conserva."
            )
        }

        val ref = tasksCollection.add(document).await()

        return ref.id
    }

    suspend fun updateTask(document: TaskDocument) {
        val updates = HashMap<String, Any>()

        updates.put("title", document.title)
        updates.put("description", document.description)
        updates.put("completed", document.completed)
        updates.put("updatedAt", document.updatedAt)

        tasksCollection
            .document(document.id)
            .update(updates)
            .await()
    }

    suspend fun deleteTask(taskId: String) {
        tasksCollection
            .document(taskId)
            .delete()
            .await()
    }
}