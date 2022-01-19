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
    setConnected(true);
}

function disconnect() {
    let reply = {};
    reply.bye = {};
    reply.bye.over = true;
    ws.send(JSON.stringify(reply))
    setConnected(false);
}

function setConnected(connected) {
    document.getElementById("connectButton").disabled = connected;
    document.getElementById("disconnectButton").disabled = !connected;
    document.getElementById("conningButton").disabled = !connected;
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
    setConnected(false);
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

function sendConning() {
    let conningMessage = {
        conning: {
            lat: 60.00234,
            long: 21.65432,
            heading: 23,
            cog: 14,
            sog: 12.3,

            position: {
                hdop: 3,
                accuracy: ">10",
                source: "GNSS"
            },

            rot: 2,

            steering: {
                modes: ["sync", "independent", "emergency"], //optional, default is ["sync", "emergency"] in case of multiple rudders (or pods)
                mode: "sync",
                port: {
                    type: "rudder",
                    range: [-35, 35, 1],   // optional, max degrees to port and starboard. Default for 'rudder' type is [-45, 45]. The third number is the stepsize.
                    set: -10,       // requested rudder angle, in degrees. Negative values indicate port helm (the trailing edge of the rudder is to port)
                    actual: 6       // actual current rudder angle
                },
                sb: {
                    type: "rudder",
                    range: [-35, 35, 1],
                    set: -10,
                    actual: 6
                }
            },

            propulsion: {
                port: {
                    type: "cpp", // controllable pitch propeller
                    dor: "right", //optional direction of rotation, "right" for clockwise as seen from abaft when going forward, which is the default.
                    range: [-100, 100, 5], // optional, for cpp pitch is given and controlled as percentage of range of actual pitch, the third value is the step size
                    set: 65,
                    actual: 65,
                    rpm: 133  // optional for a cpp
                },
                sb: {
                    type: "cpp", // controllable pitch propeller
                    range: [-100, 100, 5], // optional, for cpp pitch is given and controlled as percentage of range of actual pitch, the third value is the step size
                    set: 65,
                    actual: 65,
                    rpm: 133  // optional for a cpp
                }
            },

            stw: 13.4,
            aws: 4.5,       // apparent (relative) wind speed in knots
            awa: 56,
            depth: 14.3
        }
    };
    ws.send(JSON.stringify(conningMessage));
}