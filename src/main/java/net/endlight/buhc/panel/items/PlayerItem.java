package net.endlight.buhc.panel.items;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.endlight.buhc.player.team.TeamInfo;
import net.endlight.buhc.item.ItemIDSunName;
import net.endlight.buhc.panel.ChestInventoryPanel;
import net.endlight.buhc.panel.from.GameFrom;
import net.endlight.buhc.player.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

public class PlayerItem extends BasePlayPanelItemInstance{

    private final PlayerInfo info;

    public PlayerItem(PlayerInfo info){
        this.info = info;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Item getItem() {
        return new Item(397,3);
    }

    @Override
    public void onClick(ChestInventoryPanel inventory, Player player) {
        player.teleport(info.getPlayer());
    }

    @Override
    public void onClickButton(Player player, GameFrom shopFrom) {

    }

    @Override
    public Item getPanelItem(PlayerInfo i, int index) {
        Item item = getItem().clone();
        item.setCustomName(TextFormat.colorize('&',"&r"+info.toString()));
        //todo 这里似乎可以画个lore
        List<String> lore = new ArrayList<>();
        lore.add(TextFormat.colorize('&',"&r "));
        lore.add(TextFormat.colorize('&',"&r&7血量 &a"+this.info.getPlayer().getHealth()+" / "+this.info.getPlayer().getMaxHealth()));
        lore.add(TextFormat.colorize('&',"&r  "));
        lore.add(TextFormat.colorize('&',"&r&7击杀 &a"+this.info.getKillCount()));
        lore.add(TextFormat.colorize('&',"&r   "));
        String status = "&a存活";

        lore.add(TextFormat.colorize('&',"&r&7状态 &a"+status));
        item.setLore(lore.toArray(new String[0]));
        item.setNamedTag(item.getNamedTag().putInt("index", index));
        item.setNamedTag(item.getNamedTag().putString("player", i.getName()));

        return item;
    }

    @Override
    public ElementButton getGUIButton(PlayerInfo info) {
        TeamInfo t = this.info.getTeamInfo();
        String img = ItemIDSunName.getIDByPath(14);
        if(t != null){
            Item i = t.getTeamConfig().getTeamConfig().getBlockWoolColor();
            img = ItemIDSunName.getIDByPath(i.getId(),i.getDamage());
        }

        return new ElementButton(TextFormat.colorize('&', this.info.toString()+"\n&r生命 &c"+ this.info.getPlayer().getHealth()+" / "+ this.info.getPlayer().getMaxHealth()),new ElementButtonImageData("path",img));
    }
}
