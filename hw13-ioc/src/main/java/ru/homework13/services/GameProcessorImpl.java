package ru.homework13.services;

import ru.homework13.model.Equation;
import ru.homework13.model.GameResult;
import ru.homework13.model.Player;

import java.util.List;

public class GameProcessorImpl implements GameProcessor {

    private static final String MSG_HEADER = "check multiple table";
    private static final String MSG_INPUT_BASE = "Enter number from 1 to 10";
    private static final String MSG_RIGHT_ANSWER = "Ok\n";
    private static final String MSG_WRONG_ANSWER = "Wrong\n";

    private final IOService ioService;
    private final EquationPreparer equationPreparer;
    private final PlayerService playerService;

    public GameProcessorImpl(IOService ioService,
                             EquationPreparer equationPreparer,
                             PlayerService playerService) {
        this.ioService = ioService;
        this.equationPreparer = equationPreparer;
        this.playerService = playerService;
    }

    @Override
    public void startGame() {
        ioService.out(MSG_HEADER);
        ioService.out("---------------------------------------");
        Player player = playerService.getPlayer();
        GameResult gameResult = new GameResult(player);

        int base = ioService.readInt(MSG_INPUT_BASE);
        List<Equation> equations = equationPreparer.prepareEquationsFor(base);
        equations.forEach(e -> {
            boolean isRight = ioService.readInt(e.toString()) == e.getResult();
            gameResult.incrementRightAnswers(isRight);
            ioService.out(isRight? MSG_RIGHT_ANSWER : MSG_WRONG_ANSWER);
        });
        ioService.out(gameResult.toString());
    }
}
