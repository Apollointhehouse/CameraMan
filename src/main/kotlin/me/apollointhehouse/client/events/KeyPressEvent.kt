package me.apollointhehouse.client.events

import me.apollointhehouse.raywire.api.Event
import net.minecraft.client.input.InputDevice

class KeyPressEvent(val inputDevice: InputDevice) : Event
