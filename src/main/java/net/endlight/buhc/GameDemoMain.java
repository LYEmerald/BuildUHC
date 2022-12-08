package net.endlight.buhc;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import net.endlight.buhc.command.GameAdminCommand;
import net.endlight.buhc.command.GameCommand;
import net.endlight.buhc.command.GameSpeakCommand;
import net.endlight.buhc.manager.TotalManager;

/**

 * @author Sobadfish
 * 13:07
 */
public class GameDemoMain extends PluginBase {



    @Override
    public void onEnable() {


        this.getLogger().info(TextFormat.colorize('&',"&e正在加载"+ TotalManager.GAME_NAME+" 插件 本版本为&av"+this.getDescription().getVersion()));
        this.getLogger().info(TextFormat.colorize('&',"&a插件加载完成，祝您使用愉快"));

        TotalManager.init(this);
        this.getServer().getCommandMap().register(TotalManager.GAME_NAME,new GameAdminCommand(TotalManager.COMMAND_ADMIN_NAME));
        this.getServer().getCommandMap().register(TotalManager.GAME_NAME,new GameCommand(TotalManager.COMMAND_NAME));
        this.getServer().getCommandMap().register(TotalManager.GAME_NAME,new GameSpeakCommand(TotalManager.COMMAND_MESSAGE_NAME));

        this.getLogger().info(TextFormat.colorize('&',"&a插件加载完成，祝您使用愉快"));

    }

    @Override
    public void onDisable() {
       TotalManager.onDisable();
    }


//    public enum UiType{
//        /**
//         * auto: 自动
//         *
//         * packet: GUI界面
//         *
//         * ui: 箱子界面
//         * */
//        AUTO,PACKET,UI
//    }
}
