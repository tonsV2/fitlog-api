package dk.fitfit.fitlog.domain.assembler

import dk.fitfit.fitlog.domain.Picture
import dk.fitfit.fitlog.domain.User
import dk.fitfit.fitlog.dto.*

fun Picture.toPictureResponse() = PictureResponse(url, creator.toUserResponse(), id)
fun PictureRequest.toPicture(user: User) = Picture(url, user, id ?: 0)
