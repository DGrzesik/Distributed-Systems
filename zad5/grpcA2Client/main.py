import time

import grpc
import ski_conditions_pb2
import ski_conditions_pb2_grpc

def run():
    channel = grpc.insecure_channel('127.0.0.5:50051')
    stub = ski_conditions_pb2_grpc.SkiConditionsStub(channel)
    response = stub.SubscribeSkiConditions(ski_conditions_pb2.SkiConditionsSubscription(resorts=["Pulawy","Slotwiny Arena"],clientID=1))
    listen(response)


def listen(response):
    try:
        for i in response:
            print(i)
    except grpc._channel._Rendezvous:
        print("Connection lost - reconnecting...")
        time.sleep(1)
        run()

if __name__ == '__main__':
    run()
