package com.jr.composedemo

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

fun TextView.disableCopyPaste() {
    isLongClickable = false
    setTextIsSelectable(false)
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) { }
    }
}