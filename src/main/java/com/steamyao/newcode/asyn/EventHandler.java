package com.steamyao.newcode.asyn;

import java.util.List;

/**
 * @Package com.steamyao.newcode.asyn
 * @date 2019/8/8 10:16
 * @description
 */
public interface EventHandler {

    void doHandler(EventModel eventModel);

    List<EventType> getSupportEventTypes();
}
