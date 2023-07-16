package org.example;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooWatcher zooWatcher = new ZooWatcher();
        zooWatcher.run();
    }
}
