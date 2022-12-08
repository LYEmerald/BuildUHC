package net.endlight.buhc.event;


import cn.nukkit.plugin.Plugin;
import net.endlight.buhc.room.GameRoom;

/**
 * 房间游戏开始事件
 * @author SoBadFish
 * 2022/1/15
 */
public class GameRoomStartEvent extends GameRoomEvent {

    public GameRoomStartEvent(GameRoom room, Plugin plugin) {
        super(room, plugin);
    }
}
