import { useState } from "react";
import axios from "axios";
import styles from "./page.module.css";

const ChatUI = () => {
  const [input, setInput] = useState("");
  const [messages, setMessages] = useState([]);
  const [codeOutput, setCodeOutput] = useState('');


  const handleSubmit = async (e) => {
    e.preventDefault();

    // Send the user input to backend API
    const response = await axios.post("http://localhost:8082/api/gpt/generate", { input });

    // Append user message and AI response to chat history
    setMessages((prevMessages) => [
      ...prevMessages,
      { sender: "user", text: input },
      { sender: "ai", text: response.data.response }
    ]);

    setInput(""); // Clear the input field
  };
  const handleClear = () => {
    setMessages([]); // Clear chat history
  };

  return (
    <div className={styles.chatContainer}>
      <div className={styles.chatBox}>
        {messages.map((msg, index) => (
          <div key={index} className={msg.sender === "user" ? styles.userMessage : styles.aiMessage}>
            {msg.text}
          </div>
        ))}
      </div>
      <form className={styles.inputForm} onSubmit={handleSubmit}>
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Enter a prompt..."
          className={styles.inputField}
        />
        <button type="submit" className={styles.sendButton}>
          Send
        </button>
        <button type="button" onClick={handleClear} className={styles.eraseButton}>
          Clear
        </button>
      </form>
    </div>
  );
};

export default ChatUI;







