import { useEffect, useState } from "react";
import "./App.css";
import { Client } from "@stomp/stompjs";

const stompClient = new Client({
  brokerURL: "ws://localhost:8080/chat",
});

interface chatMessage {
  sender: string;
  content: string;
}

function App() {
  const [text, setText] = useState<string>("");
  const [messages, setMessages] = useState<chatMessage[]>([]);

  stompClient.onConnect = (frame) => {
    console.log("connected");
    stompClient.subscribe("/topic/backendOutput", (message) => {
      setMessages((messages) => [...messages, JSON.parse(message.body)]);
    });
  };

  stompClient.onWebSocketError = (error) => {
    console.error("Error with websocket", error);
  };

  stompClient.onStompError = (frame) => {
    console.error("Broker reported error: " + frame.headers["message"]);
    console.error("Additional details: " + frame.body);
  };

  const handleSend = () => {
    stompClient.publish({
      destination: "/app/backendInput",
      body: JSON.stringify({ content: text }),
    });
    setText("");
  };

  useEffect(() => {
    stompClient.activate();
    console.log("connected");
    return () => {
      stompClient.deactivate();
      console.log("disconnected");
    };
  }, []);

  return (
    <>
      <p>Messages</p>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>
            <p>{message.sender}</p>
            <p>{message.content}</p>
          </li>
        ))}
      </ul>
      <label>Enter message to send:</label>
      <input value={text} onChange={(e) => setText(e.target.value)} />
      <button onClick={handleSend}>Send</button>
    </>
  );
}

export default App;
