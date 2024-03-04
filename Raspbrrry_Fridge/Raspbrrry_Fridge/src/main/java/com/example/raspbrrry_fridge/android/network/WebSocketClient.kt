package com.example.raspbrrry_fridge.android.network

import okhttp3.*


class WebSocketClient {

    var client = OkHttpClient()

    var request: Request = Request.Builder()
        .url("ws://192.168.2.171:8765")
        .build()

    var ws = client.newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            println("connected")
            // WebSocket connection is established
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            // Handle incoming messages from the WebSocket server
            println(text)
            println("message")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            // WebSocket connection is closed
        }
    })

    fun sendMessage(messageToSend: String) {
        ws.send(messageToSend)
    }

}
