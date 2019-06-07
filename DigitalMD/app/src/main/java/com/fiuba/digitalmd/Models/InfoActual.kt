package com.fiuba.digitalmd.Models

object InfoActual {

    private  var usuario_actual : User = User("","","","","","","")
    private var medico_actual: Medico = Medico("","","","","","","")
    private var uidActual : String = ""
    fun setUsuarioActual(user : User){
        usuario_actual = user
    }

    fun getUsuarioActual():User{
        return usuario_actual
    }

    fun setMedicoActual(medico : Medico){
        medico_actual = medico
    }

    fun getMedicoActual():Medico{
        return medico_actual
    }

    fun setUidActual(uid: String) {
        uidActual = uid
    }

    fun getUidActual():String{
        return uidActual
    }
}