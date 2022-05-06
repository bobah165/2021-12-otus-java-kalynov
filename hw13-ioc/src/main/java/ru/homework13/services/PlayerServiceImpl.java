package ru.homework13.services;

import ru.homework13.model.Player;

public class PlayerServiceImpl implements PlayerService {

    private final IOService ioService;

    public PlayerServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Player getPlayer() {
        ioService.out("Who are you?");
        String playerName = ioService.readLn("Enter name: ");
        return new Player(playerName);
    }
}
