package ru.homework13.config;


import ru.homework13.appcontainer.api.AppComponent;
import ru.homework13.appcontainer.api.AppComponentsContainerConfig;
import ru.homework13.services.EquationPreparer;
import ru.homework13.services.EquationPreparerImpl;
import ru.homework13.services.GameProcessor;
import ru.homework13.services.GameProcessorImpl;
import ru.homework13.services.IOService;
import ru.homework13.services.IOServiceStreams;
import ru.homework13.services.PlayerService;
import ru.homework13.services.PlayerServiceImpl;

@AppComponentsContainerConfig(order = 1)
public class AppConfig {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer(){
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

}
