/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
   
    $.getJSON(
            'score'
            , function(result){ //manca l'identificatore perche questa funzione ha senso solo qui. Funzione anonima.
                //La chiama quando ha scaricato l'array di oggetti json.
                var scores_table = $("#tableTopFive > tbody");
                $(result).each(function(i, item){
                    var tr_html =
                            "<tr>"
                            + "<td>" + item.username + "</td>"
                            + "<td>" + item.score + "</td>"
                            + "<td>" + item.timestamp + "</td>"
                            + "</tr>";
                    scores_table.append(tr_html);
                });
            }                    
    );
});
