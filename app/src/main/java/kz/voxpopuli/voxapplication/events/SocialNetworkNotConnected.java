package kz.voxpopuli.voxapplication.events;

import java.io.Serializable;

/**
 * Created by user on 07.05.15.
 */
public class SocialNetworkNotConnected implements Serializable {

    private int networkId;

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }
}
