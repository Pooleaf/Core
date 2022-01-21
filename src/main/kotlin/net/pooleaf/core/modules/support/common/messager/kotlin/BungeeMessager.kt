package net.pooleaf.core.modules.support.common.messager.kotlin

import net.md_5.bungee.api.CommandSender
import net.pooleaf.core.modules.support.common.messager.Messager

fun CommandSender.message(message: Any) = Messager.message(this, message)