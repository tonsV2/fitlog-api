package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.domain.Video
import dk.fitfit.fitlog.dto.VideoRequest
import dk.fitfit.fitlog.dto.VideoResponse

fun Video.toVideoResponse() = VideoResponse(url, creator.toUserResponse(), id)
fun VideoRequest.toVideo(user: User) = Video(url, user, id ?: 0)
