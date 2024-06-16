import { useEffect, useState } from "react";
import "./App.css";
import { Client } from "@stomp/stompjs";
import axios from "axios";

const stompClient = new Client({
  brokerURL: "ws://localhost:8080/chat",
});

interface chatMessage {
  sender: string;
  content: string;
}

function App() {
  const [text, setText] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [messages, setMessages] = useState<chatMessage[]>([]);

  stompClient.onConnect = (frame) => {
    console.log("connected");
    stompClient.subscribe("/topic/backendOutput", (message) => {
      setMessages((messages) => [JSON.parse(message.body), ...messages]);
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
      body: JSON.stringify({
        sender: name.length == 0 ? "Anonymous" : name,
        content: text,
      }),
    });
    setText("");
  };

  const fetchRecentMessages = () => {
    axios
      .get("http://localhost:8080/api/message/latest")
      .then((res) => setMessages(res.data));
  };

  useEffect(() => {
    stompClient.activate();
    console.log("connected");
    fetchRecentMessages();
    return () => {
      stompClient.deactivate();
      console.log("disconnected");
    };
  }, []);

  return (
    <div className="home-page">
      <section className="header">
        <h1>Group chat</h1>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Your name..."
        />
      </section>

      <section className="chat-section">
        {messages.map((message, index) => (
          <article
            className={message.sender === name ? "our-chat" : "other-chat"}
            key={index}
          >
            <label>{message.sender}</label>
            <p>{message.content}</p>
          </article>
        ))}
      </section>

      <section className="input-box">
        <input value={text} onChange={(e) => setText(e.target.value)} />
        <button onClick={handleSend} disabled={text.length == 0}>
          Send
        </button>
      </section>
    </div>
  );
}

export default App;
