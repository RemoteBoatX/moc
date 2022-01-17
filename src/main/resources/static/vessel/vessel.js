let ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/vessel");
    ws.onmessage = function (e) {
        handleMessage(e.data);
    }
    document.getElementById("connectButton").disabled = true;
    document.getElementById("disconnectButton").disabled = false;
}

function disconnect() {
    ws.close();
    document.getElementById("connectButton").disabled = false;
    document.getElementById("disconnectButton").disabled = true;
}

function handleMessage(data) {
    printMessage(data);
    const messageData = JSON.parse(data);
    if (messageData.time) {
        handleLatencyMessage(messageData.time);
    }
}

function printMessage(message) {
    let messages = document.getElementById("messages");
    let newMessage = document.createElement("div");
    newMessage.innerHTML = message;
    messages.appendChild(newMessage);
}

function handleLatencyMessage(latencyMessage) {
    const now = Date.now();

    if (latencyMessage.sent && latencyMessage.received) {
        const sent = latencyMessage.sent;
        const received = latencyMessage.received;

        const latencyOutgoing = received - sent;
        const latencyIncoming = now - received;
        const latencyRoundTrip = now - sent;

        printMessage("outgoing: " + latencyOutgoing)
        printMessage("incoming: " + latencyIncoming)
        printMessage("round-trip: " + latencyRoundTrip)
    } else if (latencyMessage.sent) {
        const sent = latencyMessage.sent;
        const latencyIncoming = now - sent;
        printMessage("incoming: " + latencyIncoming)

        let reply = {};
        latencyMessage.received = now;
        reply.time = latencyMessage;
        ws.send(JSON.stringify(reply))
    } else {
        // TODO: Handle incorrect message format.
    }
}