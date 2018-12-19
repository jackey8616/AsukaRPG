package info.clo5de.asuka.rpg.exception;

public class ItemConfigException extends Exception {

    public enum Action {
        READ, WRITE
    }

    public enum Stage {
        ItemID, ItemRecipe
    }

    private Action action;
    private Stage stage;
    private String message;

    public ItemConfigException (Action action, Stage stage, String message) {
        super(message);
        this.action = action;
        this.stage = stage;
        this.message = message;
    }

    public Action getAction () {
        return this.action;
    }

    public Stage getStage () {
        return this.stage;
    }

}
