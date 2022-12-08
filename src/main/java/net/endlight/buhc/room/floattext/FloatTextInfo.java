package net.endlight.buhc.room.floattext;

import cn.nukkit.level.Position;
import net.endlight.buhc.entity.GameFloatText;
import net.endlight.buhc.manager.FloatTextManager;
import net.endlight.buhc.room.GameRoom;
import net.endlight.buhc.room.config.WorldInfoConfig;


public class FloatTextInfo {

    public FloatTextInfoConfig floatTextInfoConfig;

    public GameFloatText gameFloatText;

    public FloatTextInfo(FloatTextInfoConfig config){
        this.floatTextInfoConfig = config;
    }

    public FloatTextInfo init(GameRoom room){
        try{
            Position position = WorldInfoConfig.getPositionByString(floatTextInfoConfig.position);
            gameFloatText = GameFloatText.showFloatText(floatTextInfoConfig.name,position,"");
            if(gameFloatText != null){
                gameFloatText.room = room;
            }

        }catch (Exception e){
            return null;
        }

        return this;
    }

    /**
     * 游戏房间内浮空字更新
     * @param room 游戏房间
     * */
    public boolean stringUpdate(GameRoom room){
        //TODO 房间内浮空字更新
        String text = floatTextInfoConfig.text;
        if(room == null){
            return false;
        }
        if(room.getWorldInfo() == null){
            return false;
        }

        if(gameFloatText != null){
            if(gameFloatText.isClosed()){
                FloatTextManager.removeFloatText(gameFloatText);
                init(room);
            }
            gameFloatText.setText(text);
        }
        return true;
    }
}
