import time
import socket

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("", 8888))
server_socket.listen(5)

print ("TCPServer Waiting for client on port 8888")

while 1:
    server_socket.settimeout(1)
    try :
        client_socket, address = server_socket.accept()
    except :
        continue    
    print ("I got a connection from ", address)
    data = "1234"
    # client_socket.send(data.encode())
    sending = False
    try: 
        data = client_socket.recv(4)
        if data == 'send':
            sending = True
        else:
            sending = False
    except:
        pass
    
    if sending:
        while True:
            try:
                data = client_socket.recv(8192)
                print(len(data))
                if not data:
                    break;
            except:
                print('except')
                pass
    else:
        while True:
            bitrate = 1 * 1000000
            try:
                client_socket.send(bytearray([1] * bitrate/1000))
            except:
                pass
        client_socket.close()
    print("SOCKET closed... END")

