package me.apollointhehouse.events

import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.utils.RenderUtils

class Render3DEvent(val renderUtils: RenderUtils, val delta: Float) : Event
