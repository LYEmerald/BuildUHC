package net.endlight.buhc.room.event.defaults;

import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;

import net.endlight.buhc.player.PlayerInfo;
import net.endlight.buhc.room.GameRoom;
import net.endlight.buhc.room.config.GameRoomEventConfig;
import net.endlight.buhc.room.event.IGameRoomEvent;

public class CommandEvent extends IGameRoomEvent {

    public CommandEvent(GameRoomEventConfig.GameRoomEventItem item) {
        super(item);
    }

    @Override
    public void onStart(GameRoom room) {
        for(PlayerInfo info: room.getLivePlayers()){
            Server.getInstance().getCommandMap().dispatch(new ConsoleCommandSender(),getEventItem().value.toString().replace("@p","'"+info.getName()+"'"));
        }
    }
}
