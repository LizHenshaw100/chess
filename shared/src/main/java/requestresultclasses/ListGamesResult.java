package requestresultclasses;

import model.GameData;
import java.util.ArrayList;

public record ListGamesResult(ArrayList<GameSummary> games) { }
