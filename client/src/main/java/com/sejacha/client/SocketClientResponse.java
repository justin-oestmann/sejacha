package com.sejacha.client;

interface SocketClientResponse {
    void onLoginSuccess(SocketMessage response);

    void onLoginFail(SocketMessage response);

    void onRegisterSuccess(SocketMessage response);

    void onRegisterFail(SocketMessage response);
}