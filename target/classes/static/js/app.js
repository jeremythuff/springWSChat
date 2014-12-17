var stompClient = null;

function setConnected(connected) {
   if(connected) {
        $(".app-splash").fadeOut(300, function() {
            $(".app-content").show(300); 
        }); 
   } else {
        $(".app-content").fadeOut(300, function() {
            $(".app-splash").show(300); 
        }); 
   }
}

function connect(name) {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    
    stompClient.connect(
        {
            name: name
        }, 
        function success(res) {
            stompClient.subscribe('/WSRes/chat', function subscribed(res){
                console.log("subscribed!!!!!!!");
                setConnected(true);
            }, {name: name});
        },
        function error(res) {
            $(".connect-btn").html(res.headers.message);
            $(".screen-name").val("");
            disconnect();
        });

}

function disconnect() {
    stompClient.disconnect();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var name = document.getElementById('name').value;
    stompClient.send("/ws/chat", {}, JSON.stringify({ 'name': name }));
}

function showGreeting(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}

$(".connect-btn").on("click", function() {
    var $this = $(this);
    var name = $(".screen-name").val();

    if(name != "") {
        connect(name);
    } else {
        $this.html("Input Screen Name")
    }

    return false;

});