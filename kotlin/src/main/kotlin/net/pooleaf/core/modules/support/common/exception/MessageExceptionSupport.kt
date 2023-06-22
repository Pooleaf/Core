package net.pooleaf.core.modules.support.common.exception

fun throwMessage(message: String) {
    throw MessageException(message)
}