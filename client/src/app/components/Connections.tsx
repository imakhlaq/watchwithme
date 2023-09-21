"use client";

import SockJS from "sockjs-client";
import { Client, over } from "stompjs";
import { useRef } from "react";

type Props = {};
export default function Connections({}: Props) {
  const videoRef = useRef<HTMLVideoElement | null>(null);

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

        stompClient.subscribe("/topic/data", (body) => {
          console.log("MessageCall");
          console.log(body);
          if (videoRef !== null)
            videoRef.current!.src = "data:video/mp4;base64," + body;
        });
        //here subscribe

        stompClient.send("/app/message");
      },
      (error) => console.log(error),
    );

    //send data to server
  }

  return (
    <div className="h-screen w-screen flex flex-col justify-center items-centere gap-4">
      <button
        className="bg-slate-600 px-6 py-3 max-w-lg mx-auto"
        onClick={connect}
      >
        enter room
      </button>
      <video
        className="bg-white h-[20rem] w-[32rem]"
        ref={videoRef}
        controls
      ></video>
    </div>
  );
}
