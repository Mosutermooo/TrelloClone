package com.example.routes

import com.example.controllers.work_space_contorller.WorkSpaceController
import com.example.models.work_space.WorkSpace
import com.example.models.work_space.WorkSpaceMember
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.workSpaceRoutes(){

    val workSpaceController by inject<WorkSpaceController>()

    routing {
        route("/workspace"){
            post("/create") {
                val workSpace = call.receive<WorkSpace>()
                workSpaceController.createWorkSpace(workSpace).let {
                    call.respond(it)
                }
            }
            post("/invite") {
                val params = call.receive<WorkSpaceMember>()
                workSpaceController.inviteMemberToWorkspace(params).let {
                    call.respond(it)
                }
            }
            get("/getUserWorkspaces/{uid}") {
                val uid = call.parameters["uid"]?.toInt()!!
                workSpaceController.getUserWorkSpaces(uid).let {
                    call.respond(it)
                }
            }
            get("/getWorkSpace/{workSpaceId}"){
                val uid = call.parameters["workSpaceId"]
                workSpaceController.getWorkSpace(uid).let {
                    call.respond(it)
                }
            }

        }
    }

}