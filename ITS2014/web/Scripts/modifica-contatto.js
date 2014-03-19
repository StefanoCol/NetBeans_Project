/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    
    var contatto_id = $("#contatto_id").val();
        
    $.getJSON(
            "ContattiSQL?ContattoId=" + contatto_id,
            function(contattoViewModel){
                $("#cognome").val(contattoViewModel.cognome);
                $("#nome").val(contattoViewModel.nome);
                $("#email").val(contattoViewModel.email);
                
            }
    );
    
    $("#modifica").on('click', function(){
        
        var contatto_esistente = {
            "cognome": $("#cognome").val(),
            "nome": $("#nome").val(),
            "email": $("#email").val()
        };
        
        $.ajax({
            url: "ContattiSQL?ContattoId=" + contatto_id, 
            type:"PUT",
            data: JSON.stringify(contatto_esistente),
            success: function (result){
                window.location.href="Contatti.html";
            }
        }
        );
    });
});


