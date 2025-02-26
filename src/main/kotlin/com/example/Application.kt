package com.example

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import kotlinx.serialization.Serializable
import java.lang.Exception


fun main(){

    embeddedServer(Netty, port = 8080){

        install(ContentNegotiation){

            json()

        }

        module()

    }.start(wait = true)

}

fun Application.module(){

    install(RoutingRoot){

        route("/", method = HttpMethod.Get){

            handle {

                call.respondText("Hello There")

            }


        }

        route("/user/{username}", method = HttpMethod.Get){


            handle {

                val userName = call.parameters["username"]

                if (userName == "siddik"){

                    call.respond(message = "Hello Android Dev $userName", status = HttpStatusCode.OK)

                }else {

                    call.respondText("Hello $userName")

                }
            }

        }

        route("/loginUser", method = HttpMethod.Get) {

            handle {

                val userName = call.request.queryParameters["name"]
                val userAge = call.request.queryParameters["age"]
                val city = call.request.queryParameters["city"]

                call.respondText("Hi user $userName you're age $userAge and city $city")

            }

        }

        route("/userDetail", method = HttpMethod.Get){

            handle {

                try {

                    val userInfo = UserInfo(name = "Siddik", age = "29", city = "Pune")

                    call.respond(message = userInfo, status = HttpStatusCode.OK)

                }catch (e:Exception){

                    call.respond(message = e.message.toString(), status = HttpStatusCode.BadGateway)

                }

            }

        }

        route("/redirect", method = HttpMethod.Get){

            handle {

                call.respondRedirect("/move",permanent = false)

            }

        }

        route("/move"){


            handle {

                call.respondText("You have successfully redirected towards move...")

            }
        }

    }

}

@Serializable
data class UserInfo(
    val name:String,
    val age:String,
    val city:String
)