"use client";

import SockJS from "sockjs-client";
import { Client, over } from "stompjs";

type Props = {};
export default function Connections({}: Props) {
  function onError(e: any) {
    console.log("error call");
    console.log(e);
  }

  let stompClient: Client;

  function connect(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.preventDefault();

    const sock = new SockJS("http://localhost:8080/ws");
    stompClient = over(sock);

    stompClient.connect(
      {},
      (frame) => {
        console.log(frame);

        stompClient.subscribe("/topic/data", (message) => {
          console.log("MessageCall");
          console.log(message);
        });
        //here subscribe

        stompClient.send("/app/message", {}, JSON.stringify({}));
      },
      (error) => console.log(error),
    );

    //send data to server
  }

  function sendMessage(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    e.preventDefault();
    console.log("sending message");
  }

  return (
    <div className="h-screen w-screen flex flex-col justify-center items-centere gap-4">
      <button
        className="bg-slate-600 px-6 py-3 max-w-lg mx-auto"
        onClick={connect}
      >
        enter room
      </button>
      <button
        className="bg-slate-600 px-6 py-3 max-w-lg mx-auto"
        onClick={sendMessage}
      >
        send message
      </button>
    </div>
  );
}
