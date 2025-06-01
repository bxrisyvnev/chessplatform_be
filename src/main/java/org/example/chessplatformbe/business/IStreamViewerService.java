package org.example.chessplatformbe.business;

public interface IStreamViewerService {
    void viewerJoined(String streamId);

    void viewerLeft(String streamId);

    void broadcastViewerCount(String streamId, int count);
}
