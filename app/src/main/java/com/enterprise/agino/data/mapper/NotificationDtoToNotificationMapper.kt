package com.enterprise.agino.data.mapper

import com.enterprise.agino.data.remote.dto.NotificationDto
import com.enterprise.agino.domain.model.Notification


fun NotificationDto.toNotification() =
    Notification(
        title = title,
    )