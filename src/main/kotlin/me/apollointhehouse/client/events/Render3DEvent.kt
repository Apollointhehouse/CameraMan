package me.apollointhehouse.client.events

import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.client.utils.RenderUtils

class Render3DEvent(val renderUtils: RenderUtils, val delta: Float) : Event
