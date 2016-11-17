package com.lucerotech.aleksandrp.w8monitor.api.bus;

import com.lucerotech.aleksandrp.w8monitor.api.event.UpdateUiEvent;

/**
 * Created by AleksandrP on 17.11.2016.
 */

public class BusProvider {


    public static void send(UpdateUiEvent mEvent) {
        // TODO: 17.11.2016 тут надо сделать обновление в бд и на экранах
        int id = mEvent.getId();
        switch (id) {
            case  UpdateUiEvent.LOGIN:
                break;

            case  UpdateUiEvent.LOGIN_SOCIAL:
                break;

            case  UpdateUiEvent.REGISTER:
                break;

            case  UpdateUiEvent.PROFILE:
                break;

            case  UpdateUiEvent.MESSUREMENTS:
                break;

            case  UpdateUiEvent.MESSUREMENTS_MASS:
                break;

            case  UpdateUiEvent.UPDATE_PROFILE:
                break;

            case  UpdateUiEvent.ALARM_UPDATE:
                break;

            case  UpdateUiEvent.CHANGE_PASS:
                break;

        }

    }


}
