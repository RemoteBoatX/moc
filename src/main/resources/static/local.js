let ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/ws");
    ws.onmessage = function (e) {
        printMessage(e.data);
    }
    document.getElementById("connectButton").disabled = true;
    document.getElementById("connectButton").value = "Connected";
    document.getElementById("name").disabled = true;
}

function printMessage(data) {
    let messages = document.getElementById("messages");
    let messageData = JSON.parse(data);
    let newMessage = document.createElement("div");
    newMessage.innerHTML = messageData.name + " : " + messageData.message;
    messages.appendChild(newMessage);
}

function sendToGroupChat() {
    let messageText = document.getElementById("message").value;
    document.getElementById("message").value="";
    let name = document.getElementById("name").value;
    let messageObject = {
        name: name,
        message: messageText
    }
    ws.send(JSON.stringify(messageObject))
}