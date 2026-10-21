package com.aryannayak.nexus

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.CheckBox
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val blue = Color.rgb(37, 99, 235)
    private val ink = Color.rgb(24, 32, 52)
    private val muted = Color.rgb(110, 120, 140)
    private val paleBlue = Color.rgb(243, 247, 255)
    private val border = Color.rgb(232, 236, 244)

    private lateinit var content: LinearLayout
    private lateinit var navTasks: TextView
    private lateinit var navNotes: TextView
    private lateinit var navHabits: TextView
    private lateinit var navHome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.WHITE
        window.navigationBarColor = Color.WHITE
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
        }

        val scroll = ScrollView(this)
        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(24), dp(24), dp(28))
        }

        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(
            -1, 0, 1f
        ))

        val bottomNav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(10), dp(8), dp(10))
            setBackgroundColor(Color.WHITE)
            elevation = dp(12).toFloat()
        }

        navHome = navItem("⌂", "Home", true)
        navTasks = navItem("✓", "Tasks", false)
        navNotes = navItem("▤", "Notes", false)
        navHabits = navItem("◷", "Habits", false)

        listOf(navHome, navTasks, navNotes, navHabits).forEach {
            bottomNav.addView(it, LinearLayout.LayoutParams(
                0, dp(54), 1f
            ))
        }

        root.addView(bottomNav)
        setContentView(root)

        showHome()
    }

    private fun showHome() {
        content.removeAllViews()

        val top = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val brand = TextView(this).apply {
            text = "NEXUS"
            textSize = 25f
            letterSpacing = 0.10f
            setTextColor(blue)
            setTypeface(null, Typeface.BOLD)
        }

        val spacer = View(this)
        val avatar = TextView(this).apply {
            text = "N"
            textSize = 17f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setTypeface(null, Typeface.BOLD)
            setBackgroundColor(blue)
        }

        top.addView(brand)
        top.addView(spacer, LinearLayout.LayoutParams(0, 1, 1f))
        top.addView(avatar, LinearLayout.LayoutParams(dp(42), dp(42)))
        content.addView(top)

        addText("Your day, in one place.", 27f, ink, true, 0, 30, 0, 8)
        addText(
            "A little more clarity. A little less chaos.",
            14f, muted, false, 0, 0, 0, 24
        )

        val date = java.text.SimpleDateFormat(
            "EEEE, d MMMM",
            java.util.Locale.getDefault()
        ).format(java.util.Date())

        addText(date.uppercase(), 11f, muted, true, 0, 0, 0, 12)

        val hero = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(22), dp(20), dp(22))
            setBackgroundColor(blue)
        }

        hero.addView(TextView(this).apply {
            text = "YOUR PERSONAL SPACE"
            textSize = 11f
            letterSpacing = 0.08f
            setTextColor(Color.rgb(219, 231, 255))
            setTypeface(null, Typeface.BOLD)
        })

        hero.addView(TextView(this).apply {
            text = "Make today count."
            textSize = 24f
            setTextColor(Color.WHITE)
            setTypeface(null, Typeface.BOLD)
            setPadding(0, dp(12), 0, dp(8))
        })

        hero.addView(TextView(this).apply {
            text = "Small steps. Clear priorities. Better days."
            textSize = 13f
            setTextColor(Color.WHITE)
        })

        content.addView(hero, LinearLayout.LayoutParams(
            -1, -2
        ).apply {
            bottomMargin = dp(26)
        })

        addText("YOUR OVERVIEW", 12f, muted, true, 0, 0, 0, 12)

        val row1 = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        row1.addView(summaryCard(TaskStore(this).getTasks().count { !it.completed }.toString(), "Tasks left", "✓"),
            LinearLayout.LayoutParams(0, dp(112), 1f))
        row1.addView(summaryCard("0", "Notes saved", "▤"),
            LinearLayout.LayoutParams(0, dp(112), 1f).apply {
                leftMargin = dp(12)
            })

        content.addView(row1)

        val row2 = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        row2.addView(summaryCard("0", "Habits today", "◷"),
            LinearLayout.LayoutParams(0, dp(112), 1f).apply {
                topMargin = dp(12)
            })
        row2.addView(summaryCard("0 min", "Focus time", "◎"),
            LinearLayout.LayoutParams(0, dp(112), 1f).apply {
                leftMargin = dp(12)
                topMargin = dp(12)
            })

        content.addView(row2)

        addText("QUICK ACCESS", 12f, muted, true, 0, 28, 0, 12)

        addQuickAction("✓", "Manage your tasks",
            "Keep your priorities organised") {
            showTasks()
        }

        addQuickAction("▤", "Write a note",
            "Capture an idea before it slips away") {
            showNotes()
        }

        addQuickAction("◷", "Build a habit",
            "Make small routines part of your day") {
            showHabits()
        }

        updateNavigation("Home")
    }

    private fun summaryCard(
        value: String,
        label: String,
        symbol: String
    ): LinearLayout {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(14), dp(12), dp(10), dp(10))
            setBackgroundColor(paleBlue)
        }

        card.addView(TextView(this).apply {
            text = symbol
            textSize = 18f
            setTextColor(blue)
        })

        card.addView(TextView(this).apply {
            text = value
            textSize = 21f
            setTextColor(ink)
            setTypeface(null, Typeface.BOLD)
            setPadding(0, dp(4), 0, 0)
        })

        card.addView(TextView(this).apply {
            text = label
            textSize = 12f
            setTextColor(muted)
        })

        return card
    }

    private fun addQuickAction(
        symbol: String,
        title: String,
        subtitle: String,
        action: () -> Unit
    ) {
        val item = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(14), dp(14), dp(14), dp(14))
            setBackgroundColor(Color.WHITE)
            setOnClickListener { action() }
        }

        val icon = TextView(this).apply {
            text = symbol
            textSize = 21f
            gravity = Gravity.CENTER
            setTextColor(blue)
            setBackgroundColor(paleBlue)
        }

        item.addView(icon, LinearLayout.LayoutParams(dp(46), dp(46)))

        val texts = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), 0, 0, 0)
        }

        texts.addView(TextView(this).apply {
            text = title
            textSize = 15f
            setTextColor(ink)
            setTypeface(null, Typeface.BOLD)
        })

        texts.addView(TextView(this).apply {
            text = subtitle
            textSize = 12f
            setTextColor(muted)
            setPadding(0, dp(4), 0, 0)
        })

        item.addView(texts, LinearLayout.LayoutParams(0, -2, 1f))

        item.addView(TextView(this).apply {
            text = "›"
            textSize = 26f
            setTextColor(muted)
        })

        content.addView(item, LinearLayout.LayoutParams(-1, -2).apply {
            bottomMargin = dp(8)
        })
    }

    private fun addText(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        content.addView(TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
            setPadding(dp(left), dp(top), dp(right), dp(bottom))
        })
    }

    private fun navItem(
        symbol: String,
        label: String,
        selected: Boolean
    ): TextView {
        return TextView(this).apply {
            text = "$symbol\n$label"
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(if (selected) blue else muted)
            setOnClickListener {
                when (label) {
                    "Home" -> showHome()
                    "Tasks" -> showTasks()
                    "Notes" -> showNotes()
                    "Habits" -> showHabits()
                }
            }
        }
    }

    private fun updateNavigation(selected: String) {
        val items = listOf(
            navHome to "Home",
            navTasks to "Tasks",
            navNotes to "Notes",
            navHabits to "Habits"
        )

        items.forEach { (view, label) ->
            view.setTextColor(if (label == selected) blue else muted)
        }
    }

    private fun showPlaceholder(title: String, description: String) {
        content.removeAllViews()

        addText("NEXUS", 23f, blue, true, 0, 0, 0, 30)
        addText(title, 27f, ink, true, 0, 0, 0, 10)
        addText(description, 15f, muted, false, 0, 0, 0, 24)

        val info = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(20), dp(18), dp(20))
            setBackgroundColor(paleBlue)
        }

        info.addView(TextView(this).apply {
            text = "COMING NEXT"
            textSize = 11f
            setTextColor(blue)
            setTypeface(null, Typeface.BOLD)
        })

        info.addView(TextView(this).apply {
            text = "We're building this section step by step."
            textSize = 16f
            setTextColor(ink)
            setPadding(0, dp(10), 0, 0)
        })

        content.addView(info, LinearLayout.LayoutParams(-1, -2))
        updateNavigation(title)
    }

    private fun showTasks() {
        content.removeAllViews()
        addText("NEXUS", 23f, blue, true, 0, 0, 0, 24)
        addText("My Tasks", 27f, ink, true, 0, 0, 0, 8)
        addText(
            "Add tasks, tick them off, and keep your day organised.",
            14f, muted, false, 0, 0, 0, 20
        )

        val input = EditText(this).apply {
            hint = "What needs to get done?"
            textSize = 15f
            singleLine = true
            setTextColor(ink)
            setPadding(dp(14), dp(12), dp(14), dp(12))
        }

        content.addView(input, LinearLayout.LayoutParams(-1, -2).apply {
            bottomMargin = dp(10)
        })

        val taskList = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }
                val addButton = Button(this).apply {
            text = "Add task"
            isAllCaps = false
            setOnClickListener {
                val title = input.text.toString().trim()
                if (title.isNotEmpty()) {
                    val tasks = TaskStore(this@MainActivity).getTasks()
                    tasks.add(
                        NexusTask(
                            id = System.currentTimeMillis(),
                            title = title,
                            completed = false
                        )
                    )
                    TaskStore(this@MainActivity).saveTasks(tasks)
                    input.text.clear()
                    renderTasks(taskList)
                } else {
                    input.error = "Enter a task first"
                }
            }
        }

        content.addView(addButton, LinearLayout.LayoutParams(-1, -2).apply {
            bottomMargin = dp(24)
        })

        addText("YOUR LIST", 12f, muted, true, 0, 0, 0, 12)

content.addView(taskList, LinearLayout.LayoutParams(-1, -2))

        renderTasks(taskList)
        updateNavigation("Tasks")
    }

    private fun renderTasks(taskList: LinearLayout) {
        taskList.removeAllViews()
        val store = TaskStore(this)
        val tasks = store.getTasks()

        if (tasks.isEmpty()) {
            taskList.addView(TextView(this).apply {
                text = "No tasks yet. Add your first task above!"
                textSize = 14f
                setTextColor(muted)
                setPadding(dp(4), dp(12), dp(4), dp(12))
            })
            return
        }

        tasks.forEach { task ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(8), dp(6), dp(4), dp(6))
                setBackgroundColor(paleBlue)
            }

            val check = CheckBox(this).apply {
                text = task.title
                textSize = 14f
                setTextColor(ink)
                isChecked = task.completed
                setOnCheckedChangeListener { _, checked ->
                    val latest = store.getTasks().map {
                        if (it.id == task.id) it.copy(completed = checked)
                        else it
                    }
                    store.saveTasks(latest)
                    renderTasks(taskList)
                }
            }

            row.addView(check, LinearLayout.LayoutParams(0, -2, 1f))

            val deleteButton = Button(this).apply {
                text = "Delete"
                isAllCaps = false
                textSize = 12f
                setOnClickListener {
                    store.saveTasks(
                        store.getTasks().filterNot { it.id == task.id }
                    )
                    renderTasks(taskList)
                }
            }
            row.addView(deleteButton, LinearLayout.LayoutParams(-2, -2))

            taskList.addView(row, LinearLayout.LayoutParams(-1, -2).apply {
                bottomMargin = dp(8)
            })
        }
    }

    private fun showNotes() {
        showPlaceholder("Notes", "Keep your ideas and important thoughts together.")
    }

    private fun showHabits() {
        showPlaceholder("Habits", "Build small routines that make a difference.")
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
