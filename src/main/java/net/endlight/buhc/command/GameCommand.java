package net.endlight.buhc.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.utils.TextFormat;
import net.endlight.buhc.manager.RandomJoinManager;
import net.endlight.buhc.manager.TotalManager;
import net.endlight.buhc.panel.DisPlayWindowsFrom;
import net.endlight.buhc.panel.from.GameFrom;
import net.endlight.buhc.panel.from.button.BaseIButton;
import net.endlight.buhc.player.PlayerInfo;
import net.endlight.buhc.room.GameRoom;
import net.endlight.buhc.room.WorldRoom;
import net.endlight.buhc.room.config.GameRoomConfig;

/**
 * 玩家执行的指令
 * 玩家执行这个指令后可以加入房间，或者弹出GUI选择房间加入
 *
 * @author SoBadFish
 * 2022/1/12
 */
public class GameCommand extends Command {

    public GameCommand(String name) {
        super(name,"BuildUHC Command");
    }


    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(commandSender instanceof Player) {
            if(strings.length == 0) {
                PlayerInfo info = new PlayerInfo((Player)commandSender);
                PlayerInfo i = TotalManager.getRoomManager().getPlayerInfo((Player) commandSender);
                if(i != null){
                    info = i;
                }
                GameFrom simple = new GameFrom("&l&bBuild &aUHC &1决斗游戏", "BuildUHC是一款决斗类型的小游戏\n游戏开始后，击杀掉对手即可获得胜利！", DisPlayWindowsFrom.getId(51530, 99810));
                PlayerInfo finalInfo = info;
                simple.add(new BaseIButton(new ElementButton("随机匹配游戏房间",new ElementButtonImageData("path","textures/ui/realmsIcon"))) {
                    @Override
                    public void onClick(Player player) {
                        RandomJoinManager.joinManager.join(finalInfo,null);
                    }
                });
                for (String wname : TotalManager.getMenuRoomManager().getNames()) {
                    WorldRoom worldRoom = TotalManager.getMenuRoomManager().getRoom(wname);
                    int size = 0;
                    for (GameRoomConfig roomConfig : worldRoom.getRoomConfigs()) {
                        GameRoom room = TotalManager.getRoomManager().getRoom(roomConfig.name);
                        if (room != null) {
                            size += room.getPlayerInfos().size();
                        }
                    }
                    simple.add(new BaseIButton(new ElementButton(TextFormat.colorize('&',"&l&bBuild &aUHC" + wname +"\n&1&l游戏房间数量: &b" + worldRoom.getRoomConfigs().size()), worldRoom.getImageData())) {
                        @Override
                        public void onClick(Player player) {
                            disPlayRoomsFrom(player, wname);
                        }
                    });
                }
                simple.disPlay((Player) commandSender);
                DisPlayWindowsFrom.FROM.put(commandSender.getName(), simple);
            }else{
                PlayerInfo playerInfo = new PlayerInfo((Player) commandSender);
                PlayerInfo info = TotalManager.getRoomManager().getPlayerInfo((Player) commandSender);
                if(info != null){
                    playerInfo = info;
                }
                switch (strings[0]){
                    case "quit":
                        PlayerInfo player = TotalManager.getRoomManager().getPlayerInfo((Player) commandSender);
                        if (player != null) {
                            GameRoom room = player.getGameRoom();
                            if (room.quitPlayerInfo(player,true)) {
                                playerInfo.sendForceMessage("&a成功离开房间: &r" + room.getRoomConfig().getName());

                                room.getRoomConfig().quitRoomCommand.forEach(cmd-> Server.getInstance().dispatchCommand(commandSender,cmd));
                            }
                        }
                        break;
                    case "join":
                        if (strings.length > 1) {
                            String name = strings[1];
                            if (TotalManager.getRoomManager().joinRoom(playerInfo, name)) {
                                playerInfo.sendForceMessage("&a成功加入房间: &r"+name);
                            }
                        } else {
                            playerInfo.sendForceMessage("&c请输入房间名");
                        }
                        break;
                    case "rjoin":
                    String name = null;
                        if(commandSender.isPlayer()){
                            if(strings.length > 1){
                                name = strings[1];
                            }
                            if(name != null){
                                if("".equals(name.trim())){
                                    name = null;
                                }
                            }

                            info = new PlayerInfo((Player)commandSender);
                            PlayerInfo i = TotalManager.getRoomManager().getPlayerInfo((Player) commandSender);
                            if(i != null){
                                info = i;
                            }
                            String finalName = name;
                            RandomJoinManager.joinManager.join(info,finalName);

                        }else{
                            commandSender.sendMessage("请在控制台执行");
                        }

                        break;
                        default:break;
                }
            }
        }else{
            commandSender.sendMessage("请不要在控制台执行");
            return false;
        }
        return true;
    }
    /**
     * 将GUI菜单发送给玩家
     * @param name 菜单名称(一级按键的名称)
     * @param player 发送的用户
     *
     *
     * */
    private void disPlayRoomsFrom(Player player, String name){
        DisPlayWindowsFrom.FROM.remove(player.getName());
        GameFrom simple = new GameFrom(TotalManager.getTitle(), "请点击一个房间加入\n&e注意：由于房间人数及状态变动极快，部分房间可能已开始游戏，你将以观战者模式加入！",DisPlayWindowsFrom.getId(51530,99810));
        WorldRoom worldRoom = TotalManager.getMenuRoomManager().getRoom(name);
        PlayerInfo info = new PlayerInfo(player);
        for (GameRoomConfig roomConfig: worldRoom.getRoomConfigs()) {
            int size = 0;
            String type = "&l&a可加入";
            GameRoom room = TotalManager.getRoomManager().getRoom(roomConfig.name);
            if(room != null){
                size = room.getPlayerInfos().size();
                switch (room.getType()){
                    case START:
                        type = "&l&c已开始";
                        break;
                    case END:
                        type = "&l&b等待游戏结束";
                        break;
                        default:break;
                }
            }

            simple.add(new BaseIButton(new ElementButton(TextFormat.colorize('&',roomConfig.name+" "+type + "&r\n&1玩家数: "+size+" / " + roomConfig.getMaxPlayerSize()), worldRoom.getImageData())) {
                @Override
                public void onClick(Player player) {
                    PlayerInfo playerInfo = new PlayerInfo(player);
                    if (!TotalManager.getRoomManager().joinRoom(info,roomConfig.name)) {
                        playerInfo.sendForceMessage("&c无法加入房间");
                    }else{
                        playerInfo.sendForceMessage("&a你已加入 "+roomConfig.getName()+" 房间");
                    }
//                    if (BedWarMain.getRoomManager().hasRoom(roomConfig.name)) {
                    DisPlayWindowsFrom.FROM.remove(player.getName());

                }
            });
        }
        simple.disPlay(player);
        DisPlayWindowsFrom.FROM.put(player.getName(),simple);
    }



}
