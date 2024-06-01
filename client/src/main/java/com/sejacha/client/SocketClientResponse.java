package com.sejacha.client;

interface SocketClientResponse {
    void onPing(SocketMessage response);

    void onLoginSuccess(SocketMessage response);

    void onLoginFail(SocketMessage response);

    void onRegisterSuccess(SocketMessage response);

    void onRegisterFail(SocketMessage response);

    void onLogoutSuccess(SocketMessage response);

    void onLogoutFail(SocketMessage response);

    void onNewMessageSuccess(SocketMessage response);

    void onNewMessageFail(SocketMessage response);

    void onRoomJoinSuccess(SocketMessage response);

    void onRoomJoinFail(SocketMessage response);

    void onRoomJoinWPasswordSuccess(SocketMessage response);

    void onRoomJoinWPasswordFail(SocketMessage response);

    void onRoomLeaveSuccess(SocketMessage response);

    void onRoomLeaveFail(SocketMessage response);

    void onRoomCreateSuccess(SocketMessage response);

    void onRoomCreateFail(SocketMessage response);

    void onRoomGetInfoSuccess(SocketMessage response);

    void onRoomGetInfoFail(SocketMessage response);

    void onRoomInviteContactSuccess(SocketMessage response);

    void onRoomInviteContactFail(SocketMessage response);

    void onContactAddSuccess(SocketMessage response);

    void onContactAddFail(SocketMessage response);

    void onContactRemoveSuccess(SocketMessage response);

    void onContactRemoveFail(SocketMessage response);

    void onContactCreateDMRoomSuccess(SocketMessage response);

    void onContactCreateDMRoomFail(SocketMessage response);

}