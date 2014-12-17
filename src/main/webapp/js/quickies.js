(function(){
    console.log("Loading quickies ...")

    $.ajax({
        url: "/users",
        beforeSend: function( xhr ) {
            xhr.overrideMimeType( "text/plain; charset=x-user-defined" );
        }
    }).done(function( data ) {
        if ( console && console.log ) {
            console.log( "Sample of data:", data.slice( 0, 100 ) );
        }
    });


    $('<h3 class="panel-title"><i class="fa fa-github"></i> '+  +' <small>'+ +'</small></h3>')

    $(".jumbotron")
/*
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-github"></i> Session 1 <small>Sous-titre de la session 1</small></h3>
            </div>
            <div class="panel-body">
                Contenu de la session 1
            </div>
        </div>
*/

    console.log("Loading quickies ... OK")
})();
