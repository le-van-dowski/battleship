

$(document).ready(function () {
    function createEmptyGrid(container) {
        for (let i = 0; i < 100; i++) {
            $(container).append('<div class="cell" data-index="' + i + '"></div>');
        }
    }


    $('#start').on('click', function () {
        createEmptyGrid('#player-grid');
        createEmptyGrid('#computer-grid');
//aggiungi disabilita bottone start
        $('#start').prop('disabled', true);

        
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


    //click sul campo avversario
    $('#computer-grid').on('click', '.cell', function () {
        
        const gridCell = $(this);
        const index = gridCell.data('index');
        console.log(" grid cell index: " + index);

        //controlla a inizio gioco se la cella è già colpita
        if (gridCell.hasClass('hit') || gridCell.hasClass('miss')) {
            alert('Hai già colpito questa cella!');
            return;
        }

        $.ajax({
            url: '/battle/attack/' + index, //da cambiare in /battle/attack/
            method: 'PUT',
            success: function (response) {
                if (response.hit) {//è una response ma hit nel mio caso non funziona va cambiato con un altra boolean
                    alert('Colpito!');
                    //addclass hit alla cella per il controllo
                    gridCell.addClass('hit');
                    /*$('#computer-grid .cell').eq(index).css('background-color', 'red');*/
                } 
                if (response.sunk) {
                    alert('Nave affondata!');
                }else {
                    alert('Mancato!');
                    //addclass miss alla cella per il controllo
                    gridCell.addClass('miss');
                    /*$('#computer-grid .cell').eq(index).css('background-color', 'lightblue');*/

                }

                if (response.indexAttacco !== undefined) {
                    const playerCell = $('#player-grid .cell').eq(response.indexAttacco);
                    if (response.aiHit) {
                        playerCell.addClass('hit');
                        console.log("ai hit the cell " + response.indexAttacco);
                    } else {
                        playerCell.addClass('miss');
                        console.log("ai missed the cell " + response.indexAttacco);
                    }
                }
            },
            error: function () {
                alert('Errore nell\'attacco!');
            }
        });
    });

    $('#reset').on('click', function () {
        $('#player-grid').empty();
        $('#computer-grid').empty();
        $('#start').prop('disabled', false);
    });
    
});