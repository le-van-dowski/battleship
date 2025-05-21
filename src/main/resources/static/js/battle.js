

$(document).ready(function () {
    function createEmptyGrid(container) {
        for (let i = 0; i < 100; i++) {
            $(container).append('<div class="cell" data-index="' + i + '"></div>');
        }
    }


    $('#start').on('click', function () {
        createEmptyGrid('#player-grid');
        createEmptyGrid('#computer-grid');

        $.ajax({
            url: '/battle/newgame',
            method: 'POST',
            success: function (response) {
                console.log(response);
                let i = 1;
                response.computer.ships.forEach(ship => {
                    ship.nodes.forEach(node => {
                        const index = node.posx * 10 + node.posy;
                        $('#computer-grid .cell').eq(index).addClass('computer-ships');
                        //ships tutte dello stesso colore
                    })
                });
                response.player.ships.forEach(ship => {
                    let boolSafe = false;
                    ship.nodes.forEach(node => {
                        const index = node.posx * 10 + node.posy; //realizza l'indice (funziona)
                        //aggiunta colori diversi per ogni baraca
                        $('#player-grid .cell').eq(index).addClass('player-ships' + i);
                        //se l'index è quello la classe vine aggiunta quindi se ha quella classe è una barca da colorare
                        if ($('#player-grid .cell').hasClass('player-ships' + i)) {
                            boolSafe = true;
                        }
                    })
                    if (boolSafe)
                        //cambia colore
                        i++;
                });
            },
            error: function () {
                alert('Errore nel caricamento delle griglie!');
            }
        });

    });


    /*
    // Chiamata AJAX per ottenere le posizioni casuali
    $.ajax({
        url: '/api/popola-griglie',
        method: 'GET',
        success: function (response) {
            // response = { player: [1, 23, 45], computer: [10, 20, 30] }
            response.player.forEach(index => {
                $('#player-grid .cell').eq(index).addClass('ship');
            });
            response.computer.forEach(index => {
                $('#computer-grid .cell').eq(index).addClass('ship');
            });
        },
        error: function () {
            alert('Errore nel caricamento delle griglie!');
        }
    });
    */

    //click sul campo avversario
    $('#computer-grid').on('click', '.cell', function () {
        const index = $(this).data('index');

        $.ajax({
            url: '/api/attacca/' + index, //da cambiare in /battle/attack/
            method: 'PUT',
            success: function (response) {
                if (response.hit) {//è una response ma hit nel mio caso non funziona va cambiato con un altra boolean
                    alert('Colpito!');
                    //colora se colpita una barca
                    $('#computer-grid .cell').eq(index).css('background-color', 'red');
                } else {
                    alert('Acqua!');
                    //colora se preso acqua 
                    $('#computer-grid .cell').eq(index).css('background-color', 'lightgrey');
                }
            },
            error: function () {
                alert('Errore nell\'attacco!');
            }
        });
    });
});