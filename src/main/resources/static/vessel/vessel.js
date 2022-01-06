let ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/vessel");
    ws.onmessage = function (e) {
        handleMessage(e.data);
    }
    document.getElementById("connectButton").disabled = true;
    document.getElementById("connectButton").value = "Connected";
}

function handleMessage(data) {
    printMessage(data);

    let messageData = JSON.parse(data);

    // Handle latency message.
    if (messageData.time) {
        let now = Date.now();
        let latencyMessage = messageData.time;
        if (latencyMessage.sent && latencyMessage.received) {
            let sent = latencyMessage.sent;
            let received = latencyMessage.received;

            let latencyOutgoing = received - sent;
            let latencyIncoming = now - received;
            let latencyRoundTrip = now - sent;

            printMessage("outgoing: " + latencyOutgoing)
            printMessage("incoming: " + latencyIncoming)
            printMessage("round-trip: " + latencyRoundTrip)
        } else if (latencyMessage.sent) {
            let sent = latencyMessage.sent;
            let latencyIncoming = now - sent;
            printMessage("incoming: " + latencyIncoming)

            let latencyMessageReply = {};
            latencyMessage.received = now;
            latencyMessageReply.time = latencyMessage;
            ws.send(JSON.stringify(latencyMessageReply))
        } else {
            // TODO: Handle incorrect message format.
        }
    }
}

function printMessage(message) {
    let messages = document.getElementById("messages");
    let newMessage = document.createElement("div");
    newMessage.innerHTML = message;
    messages.appendChild(newMessage);
}