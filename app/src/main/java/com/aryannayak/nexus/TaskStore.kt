package com.aryannayak.nexus

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class NexusTask(
    val id: Long,
    val title: String,
    val completed: Boolean
)

class TaskStore(context: Context) {

    private val preferences = context.getSharedPreferences(
        "nexus_tasks",
        Context.MODE_PRIVATE
    )

    fun getTasks(): MutableList<NexusTask> {
        val result = mutableListOf<NexusTask>()

        try {
            val json = JSONArray(
                preferences.getString("tasks", "[]") ?: "[]"
            )

            for (i in 0 until json.length()) {
                val item = json.getJSONObject(i)
                result.add(
                    NexusTask(
                        id = item.getLong("id"),
                        title = item.getString("title"),
                        completed = item.getBoolean("completed")
                    )
                )
            }
        } catch (_: Exception) {
            return mutableListOf()
        }

        return result
    }

    fun saveTasks(tasks: List<NexusTask>) {
        val json = JSONArray()

        tasks.forEach { task ->
            json.put(
                JSONObject().apply {
                    put("id", task.id)
                    put("title", task.title)
                    put("completed", task.completed)
                }
            )
        }

        preferences.edit()
            .putString("tasks", json.toString())
            .apply()
    }
}
