package com.fiuba.digitalmd.Models



class User  (val nombre: String, val apellido: String, val dni: String, val email: String,
                 val fecha: String, val obraSocial: String, val userType:String, val fotosDisp:Int) {
    constructor() : this("","","","","","", "",5)


}