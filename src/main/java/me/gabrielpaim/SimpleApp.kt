package me.gabrielpaim

import com.jogamp.newt.Display
import com.jogamp.newt.NewtFactory
import com.jogamp.newt.Screen
import com.jogamp.newt.javafx.NewtCanvasJFX
import com.jogamp.newt.opengl.GLWindow
import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import tornadofx.App
import tornadofx.View
import tornadofx.label
import tornadofx.launch
import tornadofx.paddingAll
import tornadofx.vbox

class SimpleView: View() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            class ThisTestApp: App(SimpleView::class)
            launch<ThisTestApp>()
        }
    }

    override val root = vbox(10) {
        paddingAll = 30
        label("JavaFX + Opengl")
        children += createCanvas {
            width = 400.0
            height = 300.0
        }
    }

}

fun createCanvas(op: NewtCanvasJFX.()->Unit): NewtCanvasJFX {
    val jfxNewtDisplay: Display = NewtFactory.createDisplay(null, false)
    val screen: Screen = NewtFactory.createScreen(jfxNewtDisplay, 0)
    val caps = GLCapabilities(GLProfile.getMaxFixedFunc(true))
    val glWindow1: GLWindow = GLWindow.create(screen, caps)
    return NewtCanvasJFX(glWindow1).also(op)
}