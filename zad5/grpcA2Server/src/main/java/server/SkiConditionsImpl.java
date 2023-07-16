package server;


import conditions.Forecast;
import conditions.ResortConditions;
import gen.Conditions;
import gen.SkiConditionsSubscription;
import gen.SkiConditionsGrpc.SkiConditionsImplBase;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import resorts.Resort;
import subscribers.SubscriberStats;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SkiConditionsImpl extends SkiConditionsImplBase {

    private final Map<String, List<StreamObserver<Conditions>>> conditionSubscribers = new ConcurrentHashMap<>();

    private final List<String> availableResorts = Resort.getResortNames();
    private final Integer reconnectionTries = 5;
    private final Map<Integer, List<Conditions>> bufferedResponses = new ConcurrentHashMap<>();


    @Override
    public void subscribeSkiConditions(SkiConditionsSubscription request, StreamObserver<Conditions> responseObserver) {
        List<String> resorts = request.getResortsList();
        Integer clientID = request.getClientID();
        ServerCallStreamObserver<Conditions> streamObserver = (ServerCallStreamObserver<Conditions>) responseObserver;
        List<Conditions> observedConditions = new ArrayList<>();
        bufferedResponses.putIfAbsent(clientID, observedConditions);
        List<Conditions> overdueResponses = bufferedResponses.get(clientID);
        List<Conditions> loadedResponses = new ArrayList<>(overdueResponses);
        resorts.forEach(resort -> {
            if (availableResorts.contains(resort.toLowerCase())) {
                if (conditionSubscribers.containsKey(resort)) {
                    conditionSubscribers.get(resort).add(responseObserver);
                } else {
                    List<StreamObserver<Conditions>> subscribers = new ArrayList<>();
                    subscribers.add(responseObserver);
                    conditionSubscribers.putIfAbsent(resort, subscribers);
                }
            }
        });
        SubscriberStats subscriberStats = new SubscriberStats(0, true);
        loadedResponses.forEach(response -> {
            try {
                responseObserver.onNext(response);
            } catch (StatusRuntimeException e) {
                System.out.println("Connection failed...");
                subscriberStats.setStatus(false);
            }
        });
        Forecast forecast = new Forecast();
        while (subscriberStats.getStatus()) {
            try {
                conditionSubscribers.keySet().forEach(resort -> {
                    if (resorts.contains(resort)) {
                        ResortConditions conditions = forecast.getSkiConditions(resort);
                        Conditions response = Conditions.newBuilder().setResort(resort).setTemperature(conditions.getTemperature())
                                .setWindSpeed(conditions.getWindSpeed()).setIsNight(conditions.getIsNight())
                                .addSky(conditions.getSky()).addSlope(conditions.getSlope()).build();

                        try {
                            if (!streamObserver.isCancelled()) {
                                responseObserver.onNext(response);
                                Thread.sleep(2000);
                            } else {

                                if (subscriberStats.getFailedAttempts() < reconnectionTries) {
                                    List<Conditions> responseList = new ArrayList<>();
                                    bufferedResponses.putIfAbsent(clientID, responseList);
                                    bufferedResponses.get(clientID).add(response);
                                    subscriberStats.fail();

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        System.out.println("Buffered Response - Thread sleep failure");
                                    }
                                } else if (subscriberStats.getStatus()) {
                                    subscriberStats.setStatus(false);
                                }
                            }

                        } catch (InterruptedException e) {
                            System.out.println("Response - Thread sleep failure ");
                        }
                    }
                });
            } catch (StatusRuntimeException e) {
                System.out.println("Thread while loop failure");
            }

        }
        cleanup(responseObserver, clientID);
    }

    public void cleanup(StreamObserver<Conditions> responseObserver, Integer clientID) {
        conditionSubscribers.keySet().forEach(resort -> conditionSubscribers.get(resort).remove(responseObserver));
        bufferedResponses.remove(clientID);
    }
}

