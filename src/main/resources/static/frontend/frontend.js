let ws;
let requestedStreams = {};

function connect() {
    ws = new WebSocket("ws://localhost:8080/frontend");
    ws.onmessage = function (e) {
        handleMessage(e.data);
    }
    const connectButton = document.getElementById("connectButton");
    connectButton.parentNode.removeChild(connectButton);
}

function handleMessage(data) {
    const messageData = JSON.parse(data);


    const update = messageData.update;
    delete messageData.update;
    if (update === false) {
        for (let vesselId in messageData) {
            addVessel(vesselId);
        }
    }

    for (let vesselId in messageData) {
        const message = messageData[vesselId];
        if (message.connected === true) {
            addVessel(vesselId);
        } else if (message.connected === false) {
            removeVessel(vesselId);
        }
        if (message.latency) {
            printMessage(vesselId + ": " + JSON.stringify(message.latency));
        }
        if (message.vessel && message.vessel.streams) {
            handleStreamsMessage(vesselId, message.vessel.streams);
        }
        if (message.streams) {
            handleStreamsMessage(vesselId, message.streams);
        }
        if (message.conning) {
            console.log("Vessel " + vesselId + " conning:");
            console.log(message.conning);
        }
        if (message.statuses) {
            console.log("Vessel " + vesselId + " statuses:");
            console.log(message.statuses);
        }
        if (message.status) {
            console.log("Vessel " + vesselId + " status:");
            console.log(message.status);
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
    let buttons = document.createElement("div");
    buttons.id = vesselId + "-buttons";
    let disconnectButton = document.createElement("button");
    disconnectButton.innerHTML = "Disconnect";
    disconnectButton.onclick = () => disconnectVessel(vesselId);
    buttons.appendChild(disconnectButton);
    vessels.appendChild(buttons);
    requestedStreams[vesselId] = [];
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
    let buttons = document.getElementById(vesselId + "-buttons");
    buttons.parentNode.removeChild(buttons);
}

function handleStreamsMessage(vesselId, streams) {
    if (!streams || !Object.keys(streams).length) {
        return;
    }

    let buttons = document.getElementById(vesselId + "-buttons");
    for (let stream in streams) {
        createCheckbox(vesselId, stream, buttons);
    }
    let requestButton = document.createElement("button");
    requestButton.innerHTML = "Request";
    requestButton.onclick = () => requestStreams(vesselId);
    buttons.appendChild(requestButton);
}

function requestStreams(vesselId) {
    let requestMessage = {request: {}};
    for (let stream of requestedStreams[vesselId]) {
        requestMessage.request[stream] = true;
    }
    let message = {};
    message[vesselId] = requestMessage;
    ws.send(JSON.stringify(message))
}

function createCheckbox(vesselId, stream, container) {
    const checkbox = document.createElement('input');
    checkbox.type = "checkbox";
    checkbox.name = "name";
    checkbox.value = "value";
    checkbox.id = vesselId + stream;
    checkbox.onclick = () => {
        requestStream(vesselId, stream, checkbox.checked);
    }

    const label = document.createElement('label')
    label.htmlFor = checkbox.id;
    label.appendChild(document.createTextNode(stream));

    container.appendChild(checkbox);
    container.appendChild(label);
}

function requestStream(vesselId, stream, requested) {
    let requestedStreamsForVessel = requestedStreams[vesselId];
    if (requested) {
        requestedStreamsForVessel.push(stream);
    } else {
        requestedStreamsForVessel = requestedStreamsForVessel.filter(requestedStream => stream !== requestedStream);
    }
    requestedStreams[vesselId] = requestedStreamsForVessel;
}