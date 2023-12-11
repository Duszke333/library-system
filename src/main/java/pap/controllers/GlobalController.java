package pap.controllers;

import lombok.Getter;
import lombok.Setter;

public class GlobalController {
    private GlobalController() {}
    
    @Getter
    @Setter
    private static MainViewController parent;
}
