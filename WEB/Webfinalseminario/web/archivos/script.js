/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function habilita(idElemento, idElemento2) {
    document.getElementById(idElemento).style.display = "block"; //Mostrar
    document.getElementById(idElemento2).style.display = "none"; //Ocultar

    //Se afecta la propiedad display para que este muestre u oculte algo. 

}

function foco(idElemento) {
    document.getElementById(idElemento).focus();
}

function focoBoton(botonGuardar, botonModificar) {
    if (document.getElementById(botonGuardar).style.display === "block") {
        document.getElementById(botonGuardar).focus();
    }
    if (document.getElementById(botonModificar).style.display === "block") {
        document.getElementById(botonModificar).focus();
    }
}