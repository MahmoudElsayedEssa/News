package com.example.news.domain.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventDispatcher @Inject constructor() {

    private val _events = MutableSharedFlow<NewsDomainEvent>(
        replay = 0,
        extraBufferCapacity = 64
    )

    val events: SharedFlow<NewsDomainEvent> = _events.asSharedFlow()

    suspend fun dispatch(event: NewsDomainEvent) {
        _events.emit(event)
    }

    fun tryDispatch(event: NewsDomainEvent): Boolean {
        return _events.tryEmit(event)
    }
}