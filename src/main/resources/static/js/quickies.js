// Date format like PHP
Date.prototype.format=function(e){var t="";var n=Date.replaceChars;for(var r=0;r<e.length;r++){var i=e.charAt(r);if(r-1>=0&&e.charAt(r-1)=="\\"){t+=i}else if(n[i]){t+=n[i].call(this)}else if(i!="\\"){t+=i}}return t};Date.replaceChars={shortMonths:["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],longMonths:["January","February","March","April","May","June","July","August","September","October","November","December"],shortDays:["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],longDays:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],d:function(){return(this.getDate()<10?"0":"")+this.getDate()},D:function(){return Date.replaceChars.shortDays[this.getDay()]},j:function(){return this.getDate()},l:function(){return Date.replaceChars.longDays[this.getDay()]},N:function(){return this.getDay()+1},S:function(){return this.getDate()%10==1&&this.getDate()!=11?"st":this.getDate()%10==2&&this.getDate()!=12?"nd":this.getDate()%10==3&&this.getDate()!=13?"rd":"th"},w:function(){return this.getDay()},z:function(){var e=new Date(this.getFullYear(),0,1);return Math.ceil((this-e)/864e5)},W:function(){var e=new Date(this.getFullYear(),0,1);return Math.ceil(((this-e)/864e5+e.getDay()+1)/7)},F:function(){return Date.replaceChars.longMonths[this.getMonth()]},m:function(){return(this.getMonth()<9?"0":"")+(this.getMonth()+1)},M:function(){return Date.replaceChars.shortMonths[this.getMonth()]},n:function(){return this.getMonth()+1},t:function(){var e=new Date;return(new Date(e.getFullYear(),e.getMonth(),0)).getDate()},L:function(){var e=this.getFullYear();return e%400==0||e%100!=0&&e%4==0},o:function(){var e=new Date(this.valueOf());e.setDate(e.getDate()-(this.getDay()+6)%7+3);return e.getFullYear()},Y:function(){return this.getFullYear()},y:function(){return(""+this.getFullYear()).substr(2)},a:function(){return this.getHours()<12?"am":"pm"},A:function(){return this.getHours()<12?"AM":"PM"},B:function(){return Math.floor(((this.getUTCHours()+1)%24+this.getUTCMinutes()/60+this.getUTCSeconds()/3600)*1e3/24)},g:function(){return this.getHours()%12||12},G:function(){return this.getHours()},h:function(){return((this.getHours()%12||12)<10?"0":"")+(this.getHours()%12||12)},H:function(){return(this.getHours()<10?"0":"")+this.getHours()},i:function(){return(this.getMinutes()<10?"0":"")+this.getMinutes()},s:function(){return(this.getSeconds()<10?"0":"")+this.getSeconds()},u:function(){var e=this.getMilliseconds();return(e<10?"00":e<100?"0":"")+e},e:function(){return"Not Yet Supported"},I:function(){var e=null;for(var t=0;t<12;++t){var n=new Date(this.getFullYear(),t,1);var r=n.getTimezoneOffset();if(e===null)e=r;else if(r<e){e=r;break}else if(r>e)break}return this.getTimezoneOffset()==e|0},O:function(){return(-this.getTimezoneOffset()<0?"-":"+")+(Math.abs(this.getTimezoneOffset()/60)<10?"0":"")+Math.abs(this.getTimezoneOffset()/60)+"00"},P:function(){return(-this.getTimezoneOffset()<0?"-":"+")+(Math.abs(this.getTimezoneOffset()/60)<10?"0":"")+Math.abs(this.getTimezoneOffset()/60)+":00"},T:function(){var e=this.getMonth();this.setMonth(0);var t=this.toTimeString().replace(/^.+ \(?([^\)]+)\)?$/,"$1");this.setMonth(e);return t},Z:function(){return-this.getTimezoneOffset()*60},c:function(){return this.format("Y-m-d\\TH:i:sP")},r:function(){return this.toString()},U:function(){return this.getTime()/1e3}};


var renderTopVote = function(userGroupSession){
    var now = new Date();
    var eventDate = new Date(userGroupSession.eventDate);
    var name = userGroupSession.name;
    var votes = userGroupSession.votes ? userGroupSession.votes : 0;
    var $li = $('<li class="list-group-item"/>').text(name).append($('<span class="badge" />').text(votes));
    if (eventDate < new Date()){
        $("#top-votes-past").append($li);
    } else {
        $("#top-votes-future").append($li);
    }
};

function createBootstrapPanel(){
    var $panel = $('<div />').addClass('panel').addClass('panel-default');
    $panel.append($('<div class="panel-heading"/>').append($('<h3 class="panel-title"><span class="eventDate label label-info" /> <i class="fa fa-github" /> <span class="title" /></h3>')));
    $panel.append($('<div class="panel-body"/>'));
    $panel.append($('<div class="panel-footer clearfix"></div>'));
    return $panel;
};

var vote = function(){
    var id = $(this).data("userGroupSession");
    $.ajax({
        url: "/api/userGroupSession/" + id + "/vote"
    }).done(function( data ) {
        console.log("Vote enregistré pour la session " + id);
        refreshTopVotes();
    });
};

var renderUserGroupSession = function(userGroupSession){
    var eventDate = new Date(userGroupSession.eventDate).format('Y-m-d');
    var $panel = createBootstrapPanel();
    $panel.find('.panel-body').append($('<span />').text(userGroupSession.description));
    var $panelHeading = $panel.find('.panel-heading');
    $panelHeading.find("span.title").text(userGroupSession.name);
    $panelHeading.find("span.eventDate").text(eventDate);
    var $panelFooter = $panel.find('.panel-footer');
    var $btnVote = $('<button type="button" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-thumbs-up"></span></button>').data("userGroupSession", userGroupSession.id).click(vote);
    $panelFooter.append($btnVote);
    $('#userGroupSessions').append($panel);
};

function refreshUserGroupSessions(){
    $.ajax({
        url: "/api/userGroupSession"
    }).done(function( data ) {
        $('#userGroupSessions').empty();
        $.each(data, function(i, item){
            renderUserGroupSession(item);
        });
    });
}

function refreshTopVotes(){
    $.ajax({
        url: "/api/userGroupSession/vote/top"
    }).done(function(data) {
        $('.top-votes').empty();
        $.each(data, function(i, item){
            renderTopVote(item);
        });
    });
}

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
