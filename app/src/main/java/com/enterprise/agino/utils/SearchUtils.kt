package com.enterprise.agino.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce

class Watcher : TextWatcher {

    @OptIn(ObsoleteCoroutinesApi::class)
    private val channel = ConflatedBroadcastChannel<String>()

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun afterTextChanged(editable: Editable?) {
        channel.trySend(editable.toString()).isSuccess
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    @OptIn(ObsoleteCoroutinesApi::class)
    fun asFlow(): Flow<String> {
        return channel.asFlow()
    }
}

@OptIn(FlowPreview::class)
suspend fun AutoCompleteTextView.afterTextChanged(afterTextChanged: suspend (String) -> Unit) {
    val watcher = Watcher()
    this.addTextChangedListener(watcher)

    watcher.asFlow()
        .debounce(500)
        .collect { afterTextChanged(it) }
}