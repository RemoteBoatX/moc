let ws;

function connect() {
    ws = new WebSocket("ws://localhost:8080/frontend");
    ws.onmessage = function (e) {
        handleMessage(e.data);
    }
    document.getElementById("connectButton").disabled = true;
    document.getElementById("connectButton").value = "Connected";
    document.getElementById("name").disabled = true;
}

function handleMessage(data) {
    const messageData = JSON.parse(data);
    for (vesselId in messageData) {
        const message = messageData[vesselId];
        if (message.connected === true) {
            addVessel(vesselId);
        } else if (message.connected === false) {
            removeVessel(vesselId);
        }
        if (message.latency) {
            printMessage(vesselId + ": " + message.latency);
        }
    }
}

function printMessage(data) {
    let messages = document.getElementById("messages");
    let newMessage = document.createElement("div");
    newMessage.innerHTML = data;
    messages.appendChild(newMessage);
}

function addVessel(vesselId) {
    let vessels = document.getElementById("vessels");
    let newVessel = document.createElement("div");
    newVessel.id = vesselId;
    newVessel.innerHTML = vesselId;
    vessels.appendChild(newVessel);
    let disconnectButton = document.createElement("button");
    disconnectButton.id = vesselId + "-dc";
    disconnectButton.innerHTML = "Disconnect";
    disconnectButton.onclick = () => disconnectVessel(vesselId);
    vessels.appendChild(disconnectButton);
}

function disconnectVessel(vesselId) {
    let reply = {};
    reply[vesselId] = {};
    reply[vesselId].bye = true;
    ws.send(JSON.stringify(reply))
}

function removeVessel(vesselId) {
    let vessel = document.getElementById(vesselId);
    vessel.parentNode.removeChild(vessel);
    let disconnectButton = document.getElementById(vesselId + "-dc");
    disconnectButton.parentNode.removeChild(disconnectButton);
}

// TODO: Use for disconnect.
function sendToGroupChat() {
    let messageText = document.getElementById("message").value;
    document.getElementById("message").value="";
    let name = document.getElementById("name").value;
    let messageObject = {
        "vessel":
            {
                name: name,
                message: messageText
            },
        "streams": {}
    }
    ws.send(JSON.stringify(messageObject))
}