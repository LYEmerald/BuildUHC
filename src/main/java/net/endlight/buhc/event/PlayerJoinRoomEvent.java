package net.endlight.buhc.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.plugin.Plugin;
import net.endlight.buhc.player.PlayerInfo;
import net.endlight.buhc.room.GameRoom;


/**
 * 玩家加入房间事件
 * @author SoBadFish
 * 2022/1/15
 */
public class PlayerJoinRoomEvent extends PlayerRoomInfoEvent implements Cancellable {

    private boolean send;

    public PlayerJoinRoomEvent(PlayerInfo playerInfo, GameRoom room, Plugin plugin) {
        super(playerInfo, room, plugin);
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
