package com.fiuba.digitalmd.Models



class Paciente  (val nombre: String, val apellido: String, val urlImage: String, val descripcion: String,
                 val estadoDiagnostico: String, val comentarioMedico: String, val verOtro: String, val uid: String) {
    constructor() : this("","","","","","","","")


}