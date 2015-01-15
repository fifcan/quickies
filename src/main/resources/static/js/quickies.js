(function(){

    /**
     * Serialize form element into a JSON string
     * using jQuery primitive serializeArray
     */
    $.fn.serializeJSON = function(params){
        var json = {};
        $.each($(this).serializeArray(), function(i, item){
            json[item.name]=item.value;
        });
        return JSON.stringify(json);
    };

})();
