package net.endlight.buhc.room.event;

public interface IEventDurationTime {


    /**
     * 是否超时
     * */
    boolean isOutTime();

    /**
     * 每秒更新一次
     * */
    void update();

}
