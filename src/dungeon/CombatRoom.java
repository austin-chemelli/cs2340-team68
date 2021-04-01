package dungeon;

import combat.CombatController;

public class CombatRoom extends Room {

    private CombatController controller = null;

    public CombatRoom(CombatController controller) {
        super();
        setRoomType(RoomType.COMBAT);
        this.controller = controller;
    }

    public CombatRoom() {
        super();
        setRoomType(RoomType.COMBAT);
    }

    public CombatController getController() {
        return controller;
    }

    public void setController(CombatController controller) {
        this.controller = controller;
    }
}
