package vet.inpulse

import com.jogamp.newt.Display
import com.jogamp.newt.NewtFactory
import com.jogamp.newt.Screen
import com.jogamp.newt.javafx.NewtCanvasJFX
import com.jogamp.newt.opengl.GLWindow
import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GL2ES1
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.fixedfunc.GLLightingFunc
import com.jogamp.opengl.fixedfunc.GLMatrixFunc
import com.jogamp.opengl.util.Animator
import com.sun.javafx.application.LauncherImpl
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    LauncherImpl.launchApplication(App::class.java, emptyArray())
}

class App : Application() {

    private var animator: Animator? = null

    override fun start(stage: Stage) {
        Platform.setImplicitExit(true)
        val g = Group()
        val scene = Scene(g, 800.0, 600.0)
        stage.scene = scene
        stage.show()
        val jfxNewtDisplay: Display = NewtFactory.createDisplay(null, false)
        val screen: Screen = NewtFactory.createScreen(jfxNewtDisplay, 0)
        val caps = GLCapabilities(GLProfile.getMaxFixedFunc(true))
        val glWindow1: GLWindow = GLWindow.create(screen, caps)
        glWindow1.addGLEventListener(object : GLEventListener {
            private var rotateT = 0.0f

            override fun init(drawable: GLAutoDrawable) {
                val gl: GL2 = drawable.gl.gL2
                gl.glShadeModel(GLLightingFunc.GL_SMOOTH)
                gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
                gl.glClearDepth(1.0)
                gl.glEnable(GL.GL_DEPTH_TEST)
                gl.glDepthFunc(GL.GL_LEQUAL)
                gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST)
            }

            override fun reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
                val gl: GL2 = drawable.gl.gL2
                val aspect = width.toFloat() / height.toFloat()
                gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
                gl.glLoadIdentity()
                val fh = 0.5f
                val fw = fh * aspect
                gl.glFrustumf(-fw, fw, -fh, fh, 1.0f, 1000.0f)
                gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW)
                gl.glLoadIdentity()
            }

            override fun display(drawable: GLAutoDrawable) {
                val gl: GL2 = drawable.gl.gL2
                gl.glClear(GL.GL_COLOR_BUFFER_BIT)
                gl.glClear(GL.GL_DEPTH_BUFFER_BIT)
                gl.glLoadIdentity()
                gl.glTranslatef(0.0f, 0.0f, -5.0f)
                gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f)
                gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f)
                gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f)
                gl.glBegin(GL2.GL_QUADS)
                gl.glColor3f(0.0f, 1.0f, 1.0f)
                gl.glVertex3f(-1.0f, 1.0f, 0.0f)
                gl.glVertex3f(1.0f, 1.0f, 0.0f)
                gl.glVertex3f(1.0f, -1.0f, 0.0f)
                gl.glVertex3f(-1.0f, -1.0f, 0.0f)
                gl.glEnd()
                rotateT += 0.2f
            }

            override fun dispose(drawable: GLAutoDrawable?) {}
        })
        val glCanvas = NewtCanvasJFX(glWindow1)
        glCanvas.width = 800.0
        glCanvas.height = 600.0
        g.children.add(glCanvas)
        animator = Animator(glWindow1).also {
            it.start()
        }
    }

    override fun stop() {
        animator?.stop()
    }

}