import sys, Ice
import time

import Demo

demo_endpoints = "tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z"

def handle_invalid_proxy(communicator, exit_code):
    communicator.destroy()
    exit(exit_code)

def main():
    communicator = Ice.initialize(sys.argv)
    try:
        endpoints = demo_endpoints
        while True:
            print("Choose 'get', 'update' or 'subtract'")
            method_call = input(">>> ")
            if len(method_call) == 0: continue
            if (method_call == "x"):
                handle_invalid_proxy(communicator, 1)

            elif (method_call == "get"):
                category = input("input category>>> ")
                object_id = input("input object name>>> ")
                base = communicator.stringToProxy(f"{category}/{object_id}:{endpoints}")
                try:
                    ualsio = Demo.UALSIOPrx.checkedCast(base)
                except Ice.ObjectNotExistException:
                    print("Invalid proxy...")
                    continue

                value = ualsio.addGet()
                print(f"Value after adding up: {value}")

            elif (method_call == "update"):


                category = input("input category>>> ")
                object_id = input("input object name>>> ")
                base = communicator.stringToProxy(f"{category}/{object_id}:{endpoints}")
                try:
                    urdio = Demo.URDIOPrx.checkedCast(base)

                except Ice.ObjectNotExistException:
                    print("Invalid proxy...")
                    continue

                description = input("new description>>> ")
                urdio.updateDescription(description)
                print(f"Updated description: {description}")

            elif (method_call == "subtract"):
                category = input("input category>>> ")
                object_id = input("input object name>>> ")
                base = communicator.stringToProxy(f"{category}/{object_id}:{endpoints}")
                try:
                    dualsio = Demo.DUALSIOPrx.checkedCast(base)

                except Ice.ObjectNotExistException:
                    print("Invalid proxy...")
                    continue

                a = int(input("a = "))
                b = int(input("b = "))
                result = dualsio.subtract(a, b)
                print(f"Subtraction result: {result}")

            else:
                print("Invalid command")

    except Ice.ConnectionRefusedException:
        communicator.destroy()
        print("Connection failed... retrying...")
        time.sleep(1)
        main()
    except KeyboardInterrupt:
        communicator.destroy()
        exit(0)


if __name__ == "__main__":
    main()
    status=True
