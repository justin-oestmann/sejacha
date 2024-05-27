package com.sejacha.client;

interface SocketClientResponse {
    void onLoginSuccess(SocketMessage response);

    void onLoginFail(SocketMessage response);
}