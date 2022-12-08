package net.endlight.buhc.thread;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import net.endlight.buhc.room.GameRoom;
import net.endlight.buhc.room.floattext.FloatTextInfo;
import net.endlight.buhc.room.world.WorldInfo;


public class WorldInfoMasterThread extends PluginTask<Plugin> {

    private final WorldInfo worldInfo;
    private final GameRoom room;

    public WorldInfoMasterThread(GameRoom room,WorldInfo worldInfo,Plugin bedWarMain) {
        super(bedWarMain);
        this.worldInfo = worldInfo;
        this.room = room;
    }

    @Override
    public void onRun(int i) {
        if (worldInfo != null){
            worldInfo.onUpdate();
            for (FloatTextInfo floatTextInfo : room.getFloatTextInfos()) {
                if (!floatTextInfo.stringUpdate(room)) {

                    break;
                }
            }
        }
    }
}
