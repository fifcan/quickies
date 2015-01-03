(function(){
    console.log("Loading quickies ...")

    $.ajax({
        url: "/rest/users",
        beforeSend: function( xhr ) {
            xhr.overrideMimeType( "text/plain; charset=x-user-defined" );
        }
    }).done(function( data ) {
        if ( console && console.log ) {
            console.log( "Sample of data:", data.slice( 0, 100 ) );
        }
    });

    console.log("Loading quickies ... OK")
})();
