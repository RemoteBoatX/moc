let ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/vessel");
    ws.onopen = e => {
        let vesselMessage = {
            vessel: {
                streams: {
                    conning: true,
                    camera: true
                }
            }
        };
        ws.send(JSON.stringify(vesselMessage));
    };
    ws.onmessage = e => {
        handleMessage(e.data);
    };
    document.getElementById("connectButton").disabled = true;
    document.getElementById("disconnectButton").disabled = false;
}

function disconnect() {
    let reply = {};
    reply.bye = {};
    reply.bye.over = true;
    ws.send(JSON.stringify(reply))
    document.getElementById("connectButton").disabled = false;
    document.getElementById("disconnectButton").disabled = true;
}

function handleMessage(data) {
    printMessage(data);
    const messageData = JSON.parse(data);
    if (messageData.time) {
        handleLatencyMessage(messageData.time);
    } if (messageData.bye) {
        handleByeMessage();
    }
}

function printMessage(message) {
    let messages = document.getElementById("messages");
    let newMessage = document.createElement("div");
    newMessage.innerHTML = message;
    messages.appendChild(newMessage);
}

function handleByeMessage() {
    ws.close();
    document.getElementById("connectButton").disabled = false;
    document.getElementById("disconnectButton").disabled = true;
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