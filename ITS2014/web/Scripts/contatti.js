/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
   
    $.getJSON(
            'ContattiSQL'
            , function(result){ //manca l'identificatore perche questa funzione ha senso solo qui. Funzione anonima.
                //La chiama quando ha scaricato l'array di oggetti json.
                var tabella_contatti = $("#contatti > tbody");
                $(result).each(function(i, item){
                    var tr_html =
                            "<tr data-contatto-id='"+ item.ContattoId +"'>"
                            + "<td>" + item.cognome + "</td>"
                            + "<td>" + item.nome + "</td>"
                            + "<td>" + item.email + "</td>"
                            + "<td>" + "<a class='btn btn-default' href='ModificaContatto.jsp?ContattoId="+ item.ContattoId +"'>Modifica</a>" + "</td>"
                            + "<td><a href='#' class='cancella btn btn-danger'>cancella</a></td>"
                            + "</tr>";
                    tabella_contatti.append(tr_html);
                });
                    
                $(".cancella").on('click', function(e){
                   var contatto_id = $(e.target).parent().parent().attr("data-contatto-id");
                   if( confirm("sei sicuro?") === false) return;
                   else{
                       $.ajax({
                            url: "ContattiSQL?ContattoId=" + contatto_id, 
                            type:"DELETE",
                            data: null,
                            success: function (result){
                                window.location.href="Contatti.html";
                            }
                        });
                   }

               });
                

            }
                    
    );
    

});
