package com.fiuba.digitalmd.Models

object InfoActual {

    private lateinit var usuario_actual : User

    fun setUsuarioActual(user : User){
        usuario_actual = user
    }

    fun getUsuarioActual():User{
        return usuario_actual
    }
}