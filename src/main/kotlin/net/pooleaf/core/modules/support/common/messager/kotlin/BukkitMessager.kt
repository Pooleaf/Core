package net.pooleaf.core.modules.support.common.messager.kotlin

import net.pooleaf.core.modules.support.common.messager.Messager
import org.bukkit.command.CommandSender

fun CommandSender.message(message: Any) = Messager.message(this, message)