package com.example.plugins

import com.example.controllers.auth_controllers.RegistrationController
import com.example.controllers.auth_controllers.RegistrationControllerImpl
import com.example.controllers.board_controller.BoardController
import com.example.controllers.board_controller.BoardControllerImpl
import com.example.controllers.card_controllers.CardController
import com.example.controllers.card_controllers.CardControllerImpl
import com.example.controllers.task_conrollers.TaskController
import com.example.controllers.task_conrollers.TaskControllerImpl
import com.example.controllers.user_controller.UserController
import com.example.controllers.user_controller.UserControllerImpl
import com.example.controllers.work_space_contorller.WorkSpaceController
import com.example.controllers.work_space_contorller.WorkSpaceControllerImpl
import com.example.mappers.MemberMapper
import com.example.mappers.UserMapper
import com.example.mappers.WorkSpaceMapper
import com.example.mappers.WorkSpacesMembersMapper
import com.example.plugins.security.JwtTokenServiceImpl
import com.example.plugins.security.TokenConfig
import com.example.plugins.security.TokenService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val appModule = module {
    single<RegistrationController> {
        RegistrationControllerImpl(get(), get(), get())
    }
    single<UserController> {
        UserControllerImpl(get())
    }
    single<WorkSpaceController> {
        WorkSpaceControllerImpl(get(), get(), get(), get())
    }
    single<BoardController> {
        BoardControllerImpl(get())
    }
    single<CardController> {
        CardControllerImpl()
    }
    single<TaskController> {
        TaskControllerImpl()
    }
    single<UserMapper> {
        UserMapper()
    }
    single<WorkSpaceMapper> {
        WorkSpaceMapper()
    }
    single {
        MemberMapper()
    }
    single<WorkSpacesMembersMapper> {
        WorkSpacesMembersMapper()
    }
    single<TokenService> {
        JwtTokenServiceImpl()
    }
    single {
        TokenConfig(
            issuer = "http://0.0.0.0:8080",
            audience = "users",
            secret = "jwt-secret",
        )
    }
}

fun Application.configureKoin(){
    install(Koin){
        slf4jLogger()
        modules(appModule)
    }
}