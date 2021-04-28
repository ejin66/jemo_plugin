package com.github.ejin66.jemoplugin.services

import com.github.ejin66.jemoplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
