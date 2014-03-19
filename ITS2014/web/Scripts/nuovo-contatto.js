/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    $("#invia").on('click', function(){
        var nuovo_contatto = {
            "cognome": $("#cognome").val(),
            "nome": $("#nome").val(),
            "email": $("#email").val()
        };
        
        $.post(
            "ContattiSQL",
            JSON.stringify(nuovo_contatto),
            function (result){
                window.location.href="Contatti.html";
            }
        );
    });
});
